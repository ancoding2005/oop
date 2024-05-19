package entities;

import gamestates.Playing;
import utilz.Load;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private int dmg = 25;
    private BufferedImage[][]  enemy1Array,enemy2Array;
    private BufferedImage[] lightingBallImg;
    private ArrayList<Enemy1> enemy1s = new ArrayList<>();
    private ArrayList<Enemy2> enemy2s = new ArrayList<>();
//    private ArrayList<LightingBall> lightingBalls = new ArrayList<>();

    public EnemyManager(Playing playing){
        this.playing = playing;
        loadEnemyImgs();
        addEnemies();
    }

    private void addEnemies() {
        enemy1s = Load.getEnemy1();
        enemy2s = Load.getEnemy2();
    }

    public void update(int[][] lvlData,Player player){
        for (Enemy1 enemy1: enemy1s){
            if (enemy1.isActive()){
                enemy1.update(lvlData,player);
            }
        }
        for (Enemy2 enemy2: enemy2s){
            if (enemy2.isActive()){
                enemy2.update(lvlData,player);
                if (enemy2.getLightingBall().isActive()){
                    enemy2.getLightingBall().updatePos(lvlData);
                    if (enemy2.getLightingBall().getHitbox().intersects(player.getHitBox())){
                        player.changeHealth(-dmg);
                        enemy2.getLightingBall().setActive(false);
                    }
                }
            }
        }


    }

    public void draw(Graphics g,int xlvlOffset) {
        for (Enemy1 enemy1 : enemy1s) {
            if (enemy1.isActive()) {
                g.drawImage(enemy1Array[enemy1.getEnemyState()][enemy1.getAniIndex()],
                        (int) enemy1.getHitBox().x - xlvlOffset - ENEMY1_OFFSET_X + enemy1.flipX(),
                        (int) enemy1.getHitBox().y - ENEMY1_OFFSET_Y,
                        ENEMY1_WIDTH * enemy1.flipO(), ENEMY1_HEIGHT, null);
//            enemy1.drawHitBox(g,xlvlOffset);
//                enemy1.drawAttackBox(g, xlvlOffset);
            }
        }
        for (Enemy2 enemy2 : enemy2s) {
            if (enemy2.isActive()) {
                g.drawImage(enemy2Array[enemy2.getEnemyState()][enemy2.getAniIndex()],
                        (int) enemy2.getHitBox().x - xlvlOffset - ENEMY2_OFFSET_X + enemy2.flipX(),
                        (int) enemy2.getHitBox().y - ENEMY2_OFFSET_Y,
                        ENEMY2_WIDTH * enemy2.flipO(), ENEMY2_HEIGHT, null);
//                enemy2.drawHitBox(g,xlvlOffset);
                if (enemy2.getLightingBall().isActive()) {
                    g.drawImage(lightingBallImg[enemy2.getLightingBall().getAniIndex()], (int) enemy2.getLightingBall().getHitbox().x - xlvlOffset, (int) enemy2.getLightingBall().getHitbox().y, LIGHTING_BALL_WIDTH, LIGHTING_BALL_HEIGHT, null);
//                    enemy2.getLightingBall().draw(g);
                }
            }
        }

    }

    private void loadEnemyImgs() {
        enemy1Array = new BufferedImage[5][9];
        enemy2Array = new BufferedImage[5][8];
        lightingBallImg = new BufferedImage[9];
        BufferedImage temp1 = Load.getImgAtlas(Load.ENEMY_1);
        BufferedImage temp2 = Load.getImgAtlas(Load.ENEMY_2);
        BufferedImage temp3 = Load.getImgAtlas(Load.LIGHTING_BALL);

        for (int j = 0; j < enemy1Array.length; ++j){
            for(int i = 0; i < enemy1Array[0].length; ++i){
                enemy1Array[j][i] = temp1.getSubimage(i * ENEMY1_WIDTH_DEFAULT,j * ENEMY1_HEIGHT_DEFAULT,ENEMY1_WIDTH_DEFAULT,ENEMY1_HEIGHT_DEFAULT);
            }
        }
        for (int j = 0; j < enemy2Array.length; ++j){
            for(int i = 0; i < enemy2Array[j].length; ++i){
                enemy2Array[j][i] = temp2.getSubimage(i * ENEMY2_WIDTH_DEFAULT,j * ENEMY2_HEIGHT_DEFAULT,ENEMY2_WIDTH_DEFAULT,ENEMY2_HEIGHT_DEFAULT);
            }
        }
        for (int i = 0; i < lightingBallImg.length; ++i){
            lightingBallImg[i] = temp3.getSubimage(i * 64 + 18, 24,64 - 12 * 2,64 - 12 * 2);
        }
    }
    public void checkEnemyHit(Rectangle2D.Float attackBox){
        for (Enemy1 enemy1: enemy1s) {
            if (enemy1.isActive()) {
                if (attackBox.intersects(enemy1.getHitBox())) {
                    enemy1.hurt(8);
                    return;
                }
            }
        }
        for (Enemy2 enemy2: enemy2s) {
            if (enemy2.isActive()) {
                if (attackBox.intersects(enemy2.getHitBox())) {
                    enemy2.hurt(8);
                    return;
                }
            }
        }
    }
    public void resetAllEnemies(){
        for (Enemy1 enemy1 : enemy1s){
            enemy1.resetEnemy();
        }
        for (Enemy2 enemy2 : enemy2s){
            enemy2.resetEnemy();
        }
    }
}