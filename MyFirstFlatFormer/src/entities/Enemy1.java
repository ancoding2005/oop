package entities;

import MyFlatFormer.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.EnemyConstants.*;

public class Enemy1 extends Enemy{
    private Rectangle2D.Float attackBox;
    private int attackOffsetX;

    public Enemy1(float x, float y) {
        super(x, y,ENEMY1_WIDTH,ENEMY1_HEIGHT,ENEMY1);
        inithitBox(x,y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
        initAttack();
    }

    private void initAttack() {
        attackBox = new Rectangle2D.Float(x,y,(int)(62 * Game.SCALE),(int) (19 * Game.SCALE));
        attackOffsetX = (int)(Game.SCALE * 20);
    }

    public void update(int[][] lvlData,Player player){
        updateBehavior(lvlData,player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x = hitBox.x - attackOffsetX;
        attackBox.y = hitBox.y;
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
                        if (isCloseForAttack(player,Game.TILES_SIZE)){
                            newState(ATTACK);
                        }
                    }
                   move(lvlData);
                    break;
                case ATTACK:
                    if (aniIndex == 0) attackChecked = false;
                    if (aniIndex == 3 && !attackChecked){
                        checkEnemyHit(attackBox,player);
                    }
            }
        }
    }

    public int flipX(){
        if (walkDir == LEFT){
            return 0;
        } else {
            return width;
        }
    }

    public int flipO(){
        if (walkDir == LEFT){
            return 1;
        } else {
            return -1;
        }
    }

    public void drawAttackBox(Graphics g,int xlvlOffset){
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x - xlvlOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }
}
