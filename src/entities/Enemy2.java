package entities;

import MyFlatFormer.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.EnemyConstants.*;

public class Enemy2 extends Enemy{
    private int attackDis = 20 * Game.TILES_SIZE;
    private LightingBall lightingBall;
    private boolean lastBall = false;

    public Enemy2(float x, float y) {
        super(x, y,ENEMY2_WIDTH,ENEMY2_HEIGHT,ENEMY2);
        inithitBox(x,y, (int) (12 * Game.SCALE), (int) (30 * Game.SCALE));
        lightingBall = new LightingBall((int) hitBox.x , (int) ((int) hitBox.y - 10 * Game.SCALE), dir);
    }

    public void update(int[][] lvlData,Player player){
        updateBehavior(lvlData,player);
        updateAnimationTick();
    }

    public void updateBehavior(int[][] lvlData, Player player){
        if (firstUpdate){
            checkUpdateFirst(lvlData);
        }
        firstUpdate = false;
        if (inAir){
            updateInAir(lvlData);
        } else {
            switch (enemyState){
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (canSeePlayer(lvlData,player)){
                        turnTowardsPlayer(player);
                        if (isCloseForAttack(player,attackDis)){
                            newState(ATTACK);
                        }
                    }
                    move(lvlData);
                    break;
                case ATTACK:
                    aniSpeed = 40;
                    if (aniIndex == 0) attackChecked = false;
                    if (aniIndex == 2 && !attackChecked){
//                        checkEnemyHit(attackBox,player);
                        if (player.getHitBox().x < hitBox.x){
                            dir = -1;
                        }
                        else dir = 1;
                        if (dir == -1)
                            lightingBall.setPos((int) hitBox.x - ENEMY2_WIDTH/3, (int) ((int) hitBox.y - 10 * Game.SCALE));
                        else
                            lightingBall.setPos((int) hitBox.x, (int) ((int) hitBox.y - 10 * Game.SCALE));
                        lightingBall.setActive(true);
                    }
                    break;
            }
        }
    }

    public int flipX(){
        if (walkDir == LEFT){
            return width;
        } else {
            return 0;
        }
    }

    public int flipO(){
        if (walkDir == LEFT){
            return -1;
        } else {
            return 1;
        }
    }

    public LightingBall getLightingBall(){
        return  lightingBall;
    }

}
