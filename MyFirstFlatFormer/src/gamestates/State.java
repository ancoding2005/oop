package gamestates;

import MyFlatFormer.Game;
import UI.MenuButton;

import java.awt.event.MouseEvent;

public class State {
    protected Game game;
    public State(Game game){
        this.game = game;
    }
    public Game getGame(){
        return game;
    }
    public boolean isIn(MouseEvent e, MenuButton menuButton){
        return menuButton.getBounds().contains(e.getX(),e.getY());
    }
}
