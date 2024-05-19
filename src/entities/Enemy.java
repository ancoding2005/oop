package entities;

import MyFlatFormer.Game;

import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;
import static utilz.Help.*;
import static entities.Player.enemyAmount;

public abstract class Enemy extends Entity{

    protected int aniIndex,enemyState,enemyType;
    protected int aniTick,aniSpeed = 25;
    protected boolean firstUpdate = true;
    protected boolean inAir;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed = 0.35f * Game.SCALE;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDis = Game.TILES_SIZE;
    protected int maxHealth;
    protected int currentHealth;
    protected boolean active = true;
    protected boolean attackChecked;

    public Enemy(float x, float y, int width, int height,int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        inithitBox(x,y,width,height);
        maxHealth = getMaxHealth(enemyType);
        currentHealth = maxHealth;
    }

    protected void checkUpdateFirst(int[][] lvlData){
        if (!IsEntityOnFloor(hitBox,lvlData)) {
            inAir = true;
        }
    }

    protected void updateInAir(int[][] lvlData){
        if (canMoveHere(hitBox.x,hitBox.y + fallSpeed, hitBox.width, hitBox.height, lvlData)){
            hitBox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            tileY = (int) hitBox.y/Game.TILES_SIZE;
        }

    }

    protected boolean canSeePlayer(int[][] lvlData, Player player){
        int playerTiley = (int) (player.getHitBox().y / Game.TILES_SIZE);
        if (playerTiley == tileY){
            if (isPlayerInRange(player)){
                if (isSightClear(lvlData,hitBox,player.hitBox,tileY,lvlData)){
                    return true;
                }
            }
        }
        return false;
    }

    protected void turnTowardsPlayer(Player player){
        if (player.getHitBox().x > hitBox.x){
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    protected boolean isPlayerInRange(Player player) {
        int value = (int) Math.abs(player.hitBox.x - hitBox.x);
        return value <= attackDis * 5;
    }

    protected boolean isCloseForAttack(Player player,int attackDis){
        int value = (int) Math.abs(player.getHitBox().x - hitBox.x);
        return value <= attackDis;
    }

    protected void newState(int enemyState){
        this.enemyState = enemyState;
        if (enemyState == DEAD)
            enemyAmount++;
        aniIndex = 0;
        aniTick = 0;
    }

    protected void move(int[][] lvlData){
        float xSpeed = 0;

        if (walkDir == LEFT){
            xSpeed = -walkSpeed;
        } else {
            xSpeed = walkSpeed;
        }
        if (canMoveHere(hitBox.x + xSpeed,hitBox.y, hitBox.width, hitBox.height, lvlData)){
            if (IsOnFloor(hitBox,xSpeed,lvlData)){
                hitBox.x += xSpeed;
                return;
            }
        }
        changeDir();
    }

    protected void updateAnimationTick(){
        aniTick++;
        if (aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpriteAmount(enemyType,enemyState)){
                aniIndex = 0;
                switch (enemyState){
                    case ATTACK,HIT -> enemyState = IDLE;
                    case DEAD -> active = false;
                }
            }
        }
    }

    protected void changeDir() {
        if (walkDir == LEFT)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }

    public int getAniIndex(){
        return aniIndex;
    }

    public int getEnemyState(){
        return enemyState;
    }

    public void hurt(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0){
            newState(DEAD);
        } else {
            newState(HIT);
        }
    }

    protected void checkEnemyHit(Rectangle2D.Float attackBox,Player player) {
        if (attackBox.intersects(player.hitBox)){
            player.changeHealth(-getEnemyDmg(enemyType));
        }
        attackChecked = true;
    }

    public boolean isActive() {
        return active;
    }

    public void resetEnemy(){
        hitBox.x = x;
        hitBox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        active = true;
        fallSpeed = 0;
    }
}
