package gamestates;

import MyFlatFormer.Game;
import UI.MenuButton;
import utilz.Load;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements Statemethods{
    private MenuButton[] menuButtons = new MenuButton[3];
    private BufferedImage imgBackground, backgroundPause;
    private int menuX,menuY,menuW,menuH;
    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
        loadBackgroundPause();
    }

    private void loadBackgroundPause() {
        backgroundPause = Load.getImgAtlas(Load.BACKGROUNDPAUSE);
    }

    private void loadBackground() {
        imgBackground = Load.getImgAtlas(Load.MENU_BACKGROUND);
        menuW = (int)(imgBackground.getWidth() * Game.SCALE);
        menuH = (int)(imgBackground.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH/2 - menuW/2;
        menuY = (int) (45 * Game.SCALE);
    }

    private void loadButtons() {
        menuButtons[0] = new MenuButton(Game.GAME_WIDTH/2,(int)(150 * Game.SCALE),0,Gamestate.PLAYING);
        menuButtons[1] = new MenuButton(Game.GAME_WIDTH/2,(int)(220 * Game.SCALE),1,Gamestate.OPTION);
        menuButtons[2] = new MenuButton(Game.GAME_WIDTH/2,(int)(290 * Game.SCALE),2,Gamestate.EXIT);
    }

    @Override
    public void update() {
        for (MenuButton menuButton : menuButtons){
            menuButton.update();
        }
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(backgroundPause,0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT,null);
        g.drawImage(imgBackground,menuX,menuY,menuW,menuH,null);

        for (MenuButton menuButton : menuButtons){
            menuButton.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton menuButton : menuButtons){
            if (isIn(e,menuButton)){
                menuButton.setMousePressed(true);
                game.getPlaying().getSoundInGame().stop();
                game.getPlaying().getSoundBackground().stop();
                game.getPlaying().getSoundDead().stop();
                game.getPlaying().getSoundInGame().play();
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton menuButton : menuButtons){
            if (isIn(e,menuButton)){
                menuButton.applyGamestate();
                break;
            }
        }
        resetButton();
    }

    private void resetButton() {
        for (MenuButton menuButton : menuButtons){
            menuButton.reset();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton menuButton: menuButtons){
            menuButton.setMouseOver(false);
        }
        for (MenuButton menuButton: menuButtons){
            if (isIn(e,menuButton)) {
                menuButton.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            Gamestate.state = Gamestate.PLAYING;
            game.getPlaying().getSoundInGame().stop();
            game.getPlaying().getSoundBackground().stop();
            game.getPlaying().getSoundDead().stop();
            game.getPlaying().getSoundInGame().play();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
