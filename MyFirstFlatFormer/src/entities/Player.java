package entities;

import MyFlatFormer.Game;
import gamestates.Playing;
import utilz.Load;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Help.*;

public class Player extends Entity{

    private BufferedImage[][] animations;
    private int aniTick,aniIndex,aniSpeed = 25;
    private int playerAction = IDLE;
    private boolean moving = false,attacking = false;
    private boolean up,right,left,down, jump;
    private float playerSpeed = 1.0f * Game.SCALE;
    private int[][] levelData;
    private float xOffet = 22 * Game.SCALE;
    private float yOffet = 20 * Game.SCALE;
    private float jumpSpeed = (float) (-2.5 * Game.SCALE);
    private float gravity = 0.04f * Game.SCALE;
    private boolean inAir = false;
    private float xSpeed = 0f;
    private float ySpeed = 0f;
    private float fallAfterCollision = (float) (0.5 * Game.SCALE);

    private BufferedImage statusBar;
    private int statusBarWidth = (int) (192 * Game.SCALE);
    private int statusBarHeight = (int) (58 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);

    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarX = (int) (34 * Game.SCALE);
    private int healthBarY = (int) (14 * Game.SCALE);

    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;

    private int flipX = 0;
    private int flipO = 1;

    private boolean attackChecked;
    public static int enemyAmount = 0;

    private Rectangle2D.Float attackBox;
    private Playing playing;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y,width,height);
        this.playing = playing;
        loadAnimations();
        inithitBox(x,y,(int)(12 * Game.SCALE),(int)(30 * Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y,(int)(40 * Game.SCALE),(int)(20 * Game.SCALE));
    }

    public void update(){
        updateHealthBar();

        if (enemyAmount == 22){
            playing.setGameWin(true);
            playing.getSoundBackground().stop();
            playing.getSoundInGame().stop();
            playing.getSoundDead().stop();
            playing.getSoundWin().play();
            return;
        }

        if (currentHealth <= 0 || hitBox.y >= Game.GAME_HEIGHT - Game.TILES_SIZE - 1){
            playing.setGameOver(true);
            playing.getSoundBackground().stop();
            playing.getSoundInGame().stop();
            playing.getSoundDead().stop();
            playing.getSoundDead().play();
            return;
        }

        aniSpeed = 25;
        updateHealthBar();
        updateAttackBox();
        updatePos();
        if (attacking){
            aniSpeed = 10;
            checkAttack();
        }
        updateAnimationTick();
        setAnimation();

    }

    private void checkAttack() {
        if (attackChecked || aniIndex != 2)
            return;
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
    }

    private void updateAttackBox() {
        if (right){
            attackBox.x = hitBox.x + hitBox.width + (int)(Game.SCALE * 10);
        } else if (left){
            attackBox.x = hitBox.x - hitBox.width - (int)(Game.SCALE * 10);
        }
        attackBox.y = hitBox.y + (Game.SCALE * 10);
    }

    private void updateHealthBar() {
        healthWidth = (int)((currentHealth / (float)maxHealth) * healthBarWidth);

    }

    public void render(Graphics g,int xlvlOffset){
        g.drawImage(animations[playerAction][aniIndex],
                (int)(hitBox.x - xOffet) - xlvlOffset + flipX,
                (int)(hitBox.y - yOffet),width * flipO,height,null);
//        drawHitBox(g,xlvlOffset);
        drawAttackBox(g,xlvlOffset);
        drawUI(g);
    }

    private void drawAttackBox(Graphics g, int xlvlOffset) {
//        g.setColor(Color.red);
//        g.drawRect((int) attackBox.x - xlvlOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBar,statusBarX,statusBarY,statusBarWidth,statusBarHeight,null);
        g.setColor(Color.red);
        g.fillRect(healthBarX + statusBarX,healthBarY + statusBarY,healthWidth,healthBarHeight);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getAniAmount(playerAction)){
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    public void changeHealth(int value){
        currentHealth += value;

        if (currentHealth <= 0){
            currentHealth = 0;
        } else if (currentHealth >= maxHealth){
            currentHealth = maxHealth;
        }
    }

    private void updatePos() {

        moving = false;
        if (jump){
            jump();
        }
        if (!left && !right && !inAir){
            return;
        }
        if (!inAir){
            if ((!left && !right)||(right && left))
                return;
        }

        xSpeed = 0;
        if (left){
            xSpeed -= playerSpeed;
            flipX = width;
            flipO = -1;
        }
        if (right){
            xSpeed += playerSpeed;
            flipX = 0;
            flipO = 1;
        }

        if (!inAir)
            if (!IsEntityOnFloor(hitBox, levelData))
                inAir = true;

        if (inAir){
            if (canMoveHere(hitBox.x,hitBox.y + ySpeed, hitBox.width, hitBox.height, levelData)){
                hitBox.y += ySpeed;
                ySpeed += gravity;
                updatePosX();
            } else {
//                hitBox.y = getPosUnderFloor(hitBox,ySpeed,levelData);
                if (ySpeed > 0){
                    inAir = false;
                    ySpeed = 0;
                } else {
                    ySpeed = fallAfterCollision;
                }
                updatePosX();
            }
        }else{
            updatePosX();
        }
        moving = true;
    }

    private void jump() {
        if (inAir) return;
        inAir = true;
        ySpeed = jumpSpeed;
    }

    private void updatePosX() {
        if (canMoveHere(hitBox.x + xSpeed,hitBox.y, hitBox.width, hitBox.height, levelData)){
            hitBox.x += xSpeed;
        } else {
//            hitBox.x = getXHitBoxNeFxtToWall(hitBox,xSpeed,levelData);
        }
    }

    private void setAnimation() {

        int startAni = playerAction;

        if (!moving) {
            playerAction = IDLE;
        } else {
            playerAction = RUNNING;
        }

        if (inAir){
            if (ySpeed < 0){
                playerAction = JUMP;
            } else {
                playerAction = FALLING;
            }
        }

        if (attacking){
            playerAction = ATTACK_JUMP_2;
            setLeft(false);
            setRight(false);
        }
        if (startAni != playerAction){
            resetAniTick();
        }

    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump){
        this.jump = jump;
    }

    private void loadAnimations() {
        BufferedImage img = Load.getImgAtlas(Load.PLAYER_ATLAS);
        animations = new BufferedImage[9][14];
        for (int i = 0; i < animations.length; ++i){
            for (int j = 0; j < animations[i].length; ++j){
                animations[i][j] = img.getSubimage(j * 128 ,i * 128,128,128);
            }
        }

        statusBar = Load.getImgAtlas(Load.HEALTH_POWER);
    }
    public void resetDirBooleans(){
        left = false;
        right = false;
        up = false;
        down = false;
    }
    public void setAttacking(boolean attacking){
        this.attacking = attacking;
    }
    public void loadLevelData(int[][] levelData){
        this.levelData = levelData;
        if (!IsEntityOnFloor(hitBox,levelData)){
            inAir = true;
        }
    }
    public void setJumping(boolean jumping){
        this.jump = jump;
    }

    public void resetAll() {
        playing.getSoundInGame().stop();
        playing.getSoundBackground().stop();
        playing.getSoundDead().stop();
        enemyAmount = 0;
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        playerAction = IDLE;
        currentHealth = maxHealth;

        hitBox.x = x;
        hitBox.y = y;

        if (!IsEntityOnFloor(hitBox, levelData))
            inAir = true;
        playing.getSoundBackground().play();
    }
}
