package Levels;

import MyFlatFormer.Game;
import utilz.Load;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Load.LEVEL_ATLAS;
import static utilz.Load.getImgAtlas;

public class LevelManager {

    private Game game;
    private BufferedImage[] levelImg;
    private Level levelOne;

    public LevelManager(Game game){

        importLevelImg();
        this.game = game;
        levelOne = new Level(Load.GetLevelData());
    }

    private void importLevelImg() {
        BufferedImage img = getImgAtlas(LEVEL_ATLAS);
        levelImg = new BufferedImage[48];
        for (int j = 0; j < 4; ++j){
            for (int i = 0; i < 12;++i){
                int index = j * 12 + i;
                levelImg[index] = img.getSubimage(i * 32,j * 32,32,32);
            }
        }
    }

    public void draw(Graphics g,int xlvlOffset){

        for (int j = 0; j < game.TILES_HEIGHT; ++j){
            for (int i = 0; i < levelOne.getLevelData()[0].length ; ++i){
                int index = levelOne.getSpriteIndex(i,j);
                g.drawImage(levelImg[index],game.TILES_SIZE * i - xlvlOffset,game.TILES_SIZE * j,game.TILES_SIZE,game.TILES_SIZE,null);
            }
        }

    }
    public void update(){

    }
    public Level getCurrentLevel(){
        return levelOne;
    }

}
