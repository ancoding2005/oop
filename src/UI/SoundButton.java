package UI;

import utilz.Constants;
import utilz.Load;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import utilz.Constants.UI.PauseButtons.*;

import static utilz.Constants.UI.PauseButtons.*;

public class SoundButton extends PauseButtons{

    private BufferedImage[][] soundImgs;
    private boolean mouseOver,mousePressed;
    private boolean muted;
    private int rowIndex,colIndex;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadSoundImg();
    }

    private void loadSoundImg() {
        BufferedImage temp = Load.getImgAtlas(Load.SOUND_BUTTONS);
        soundImgs = new BufferedImage[2][3];
        for (int j = 0; j < soundImgs.length; ++j){
            for (int i = 0; i < soundImgs[j].length; ++i){
                soundImgs[j][i] = temp.getSubimage(i * SoundSizeDefault,j * SoundSizeDefault,SoundSizeDefault,SoundSizeDefault);
            }
        }
    }
    public void update(){
        if (muted){
            rowIndex = 1;
        } else {
            rowIndex = 0;
        }
        colIndex = 0;
        if (mouseOver){
            colIndex = 1;
        }
        if (mousePressed){
            colIndex = 2;
        }

    }
    public void draw(Graphics g){
        g.drawImage(soundImgs[rowIndex][colIndex],x,y,width,height,null);
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
    public void reset(){
        mousePressed = false;
        mouseOver = false;
    }
}
