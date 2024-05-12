package MyFlatFormer;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel extends JPanel {

    private float x = 100,y = 100;
    private MouseInputs mouseInputs;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int aniTick,aniIndex,aniSpeed = 15;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false;


    public GamePanel(){
        mouseInputs = new MouseInputs(this);

        importImg();
        loadAnimations();

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimations() {
        animations = new BufferedImage[9][6];
        for (int i = 0; i < animations.length; ++i){
            for (int j = 0; j < animations[i].length; ++j){
                animations[i][j] = img.getSubimage(j * 64,i * 40,64,40);
            }
        }
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                is.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void setPanelSize() {
        Dimension dimension = new Dimension(1280,800);
        setPreferredSize(dimension);
    }

    public void setDirection(int direction){
        this.playerDir = direction;
        moving = true;
    }

    public void setMoving(boolean moving){
        this.moving = moving;
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getAniAmount(playerAction)){
                aniIndex = 0;
            }
        }
    }

    private void updatePos() {
        if (moving){
            switch (playerDir){
                case LEFT:
                    x -= 5;
                    break;
                case RIGHT:
                    x += 5;
                    break;
                case UP:
                    y -= 5;
                    break;
                case DOWN:
                    y += 5;
                    break;
            }
        }
    }

    public void updateGame(){
        updateAnimationTick();
        setAnimation();
        updatePos();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(animations[playerAction][aniIndex],(int)x,(int)y,128,80,null);
    }



    private void setAnimation() {
        if (!moving) {
            playerAction = IDLE;
        } else {
            playerAction = RUNNING;
        }

    }


}
