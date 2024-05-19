package entities;

import MyFlatFormer.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import static utilz.Constants.EnemyConstants.*;
import static utilz.Help.isSolid;

public class LightingBall {
    Rectangle2D.Float hitbox;
    private boolean active = true;
    private int aniTick,aniIndex,aniSpeed = 15;
    private int offsetY = (int) (18 * Game.SCALE);

    public LightingBall(int x,int y,int dir){
        hitbox = new Rectangle2D.Float(x,y + offsetY,LIGHTING_BALL_WIDTH/2,LIGHTING_BALL_DEFAULT_HEIGHT/2);
    }
    public void updatePos(int[][] lvlData){
        updateAnimationTick();
        hitbox.x += dir * SPEED;
        if (isSolid(hitbox.x, hitbox.y,lvlData)){
            active = false;
        }
    }
    public void setPos(int x,int y){
        hitbox.x = x;
        hitbox.y = y + offsetY;
    }
    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }
    public void draw(Graphics g){
//        g.setColor(Color.red);
//        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }
    public void getActive() {
        this.active = active;
    }
    public boolean isActive(){
        return active;
    }
    protected void updateAnimationTick(){
        aniTick++;
        if (aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= 9){
                aniIndex = 0;
            }
        }
    }
    protected int getAniIndex(){
        return aniIndex;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
