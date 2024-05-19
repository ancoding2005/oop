package UI;

import MyFlatFormer.Game;
import gamestates.Playing;
import utilz.Load;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static utilz.Constants.UI.PauseButtons.*;

public class PausePlayer {

    private BufferedImage background;
    private SoundButton musicButton,sfxButton;
    private int x,y,w,h;
    private Playing playing;

    public PausePlayer(Playing playing){
        loadBackgroundImg();
        createSoundButton();
        this.playing = playing;
    }

    private void createSoundButton() {
        int soundX = (int)(450 * Game.SCALE);
        int musicy = (int) (145 * Game.SCALE);
        int sfxY = (int) (190 * Game.SCALE);
        musicButton = new SoundButton(soundX,musicy,SoundSize,SoundSize);
        sfxButton = new SoundButton(soundX,sfxY,SoundSize,SoundSize);
    }

    private void loadBackgroundImg() {
        background = Load.getImgAtlas(Load.PAUSE_MENU);
        w = (int) (background.getWidth() * Game.SCALE);
        h = (int) (background.getHeight() * Game.SCALE);
        x = Game.GAME_WIDTH/2 - w/2;
        y = Game.GAME_HEIGHT/2 - h/2;
    }

    public void update(){
        musicButton.update();
        sfxButton.update();
    }
    public void draw(Graphics g){
        g.drawImage(background,x,y,w,h,null);
        musicButton.draw(g);
        sfxButton.draw(g);
    }
    public void mouseMoved(){

    }
    public void mousePressed(MouseEvent e) {
        if (isIn(e,musicButton)){
            musicButton.setMousePressed(true);
        } else if (isIn(e,sfxButton)) {
            sfxButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e,musicButton)){
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
                if (musicButton.isMuted()){
                    playing.getSoundInGame().stop();
                } else {
                    playing.getSoundInGame().play();
                }
            }
        } else if (isIn(e,sfxButton)) {
            if(sfxButton.isMousePressed()){
                sfxButton.setMuted(!sfxButton.isMuted());
                if (sfxButton.isMuted()){
                    playing.getSoundInGame().stop();
                } else {
                    playing.getSoundInGame().play();
                }
            }
        }
        musicButton.reset();
        sfxButton.reset();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);

        if (isIn(e,musicButton)){
            musicButton.setMouseOver(true);
        } else if (isIn(e,sfxButton)) {
            sfxButton.setMouseOver(true);
        }
    }
    private boolean isIn(MouseEvent e, SoundButton b){
        return b.getBounds().contains(e.getX(),e.getY());
    }
}
