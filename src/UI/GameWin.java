package UI;

import MyFlatFormer.Game;
import gamestates.Gamestate;
import gamestates.Playing;
import utilz.Load;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GameWin {

    private Playing playing;
    private BufferedImage background;

    public GameWin(Playing playing){
        this.playing = playing;
        background = Load.getImgAtlas(Load.GAME_VICTORY);
    }

    public void draw(Graphics g){
        g.setColor(new Color(0,0,0,255));
        g.fillRect(0,0, Game.GAME_WIDTH,Game.GAME_HEIGHT);
        g.drawImage(background,0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT,null);
    }

    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            playing.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }


}
