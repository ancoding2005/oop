package utilz;

import MyFlatFormer.Game;
import entities.Enemy1;
import entities.Enemy2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import static utilz.Constants.EnemyConstants.ENEMY1;
import static utilz.Constants.EnemyConstants.ENEMY2;

public class Load {

    public static final String  PLAYER_ATLAS = "player_sprites.png";
    public static final String  LEVEL_ATLAS = "outside_sprites.png";
    public static final String LEVEL_ONE = "level_one_data_long.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String PAUSE_MENU = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String BACKGROUNDPAUSE = "background_test.png";
    public static final String BACKGROUND = "backgroundCastles.png";
    public static final String ENEMY_1 = "crabby_sprite.png";
    public static final String HEALTH_POWER = "health_power_bar.png";
    public static final String ENEMY_2 = "enemy2.png";
    public static final String LIGHTING_BALL = "Charge.png";
    public static final String GAME_OVER = "661.jpg";
    public static final String GAME_VICTORY = "victory.jpg";


    public static BufferedImage  getImgAtlas(String name){
        BufferedImage img = null;
        InputStream is = Load.class.getResourceAsStream("/" + name);
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
        return img;
    }

    public static ArrayList<Enemy1> getEnemy1() {
        BufferedImage img = getImgAtlas(LEVEL_ONE);
        ArrayList<Enemy1> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); ++j) {
            for (int i = 0; i < img.getWidth(); ++i) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == ENEMY1) {
                    list.add(new Enemy1(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
                }
            }
        }
        return list;
    }

    public static ArrayList<Enemy2> getEnemy2() {
        BufferedImage img = getImgAtlas(LEVEL_ONE);
        ArrayList<Enemy2> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); ++j) {
            for (int i = 0; i < img.getWidth(); ++i) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == ENEMY2) {
                    list.add(new Enemy2(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
                }
            }
        }
        return list;
    }

    public static int[][] GetLevelData(){

       BufferedImage img = getImgAtlas(LEVEL_ONE);
        int[][] levelData = new int[img.getHeight()][img.getWidth()];

       for (int j = 0; j < img.getHeight(); ++j){
           for (int i = 0; i < img.getWidth(); ++i){
               Color color = new Color(img.getRGB(i,j));
               int value = color.getRed();
               if (value >= 48){
                   value = 0;
               }
               levelData[j][i] = value;
           }
       }
       return levelData;
    }
}
