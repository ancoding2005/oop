package UI;

import gamestates.Gamestate;
import utilz.Load;
import static utilz.Constants.UI.Button.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuButton {
    private int x,y,rowIndex,index;
    private int xOffset = ButtonWidth/2;
    private Gamestate state;
    private BufferedImage[] imgs;
    private boolean mouseOver,mousePressed;
    private Rectangle bounds;

    public void initBounds() {
        bounds = new Rectangle(x - xOffset,y,ButtonWidth,ButtonHeight);
    }

    public MenuButton(int x, int y, int rowIndex, Gamestate state){
        this.x = x;
        this.y = y;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImg();
        initBounds();
    }

    private void loadImg() {
        imgs = new BufferedImage[3];
        BufferedImage imgB = Load.getImgAtlas(Load.MENU_BUTTONS);
        for (int i = 0; i < imgs.length; ++i){
            imgs[i] = imgB.getSubimage(i * ButtonWidthDefault,rowIndex * ButtonHeightDefault,ButtonWidthDefault,ButtonHeightDefault);
        }
    }
    public void draw(Graphics g){
        g.drawImage(imgs[index],x - xOffset,y,ButtonWidth,ButtonHeight,null);
    }
    public void update(){
        index = 0;
        if (mouseOver){
            index = 1;
        }
        if (mousePressed){
            index = 2;
        }
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void applyGamestate(){
        Gamestate.state = state;
    }

    public void reset(){
        mouseOver = false;
        mousePressed = false;
    }
    public Rectangle getBounds(){
        return bounds;
    }

}
