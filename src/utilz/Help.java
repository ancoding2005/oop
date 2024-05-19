package utilz;

import MyFlatFormer.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Help {

    public static boolean canMoveHere(float x,float y,float width,float height,int[][] levelData){
        if (!isSolid(x,y,levelData)){
            if (!isSolid(x + width,y,levelData)){
                if (!isSolid(x,y + height,levelData)){
                    if (!isSolid(x + width,y + height,levelData)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean isSolid(float x, float y, int[][] levelData){
        int maxWidth = levelData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth){
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT){
            return true;
        }
        float indexX = x/Game.TILES_SIZE;
        float indexY = y/Game.TILES_SIZE;
        return IsTileSolid((int) indexX, (int) indexY,levelData);
    }
    private static boolean IsTileSolid(int indexX,int indexY,int[][] levelData){
        int value = levelData[(int)indexY][(int)indexX];
        if (value >= 48 || value < 0 || value != 11){
            return true;
        }
        return false;
    }
    public static boolean IsAllTileWalkable(int xStart,int xEnd,int y,int[][] levelData){
        for (int i = 0; i < xEnd - xStart; ++i){
            if (IsTileSolid(xStart + i,y,levelData)){
                return false;
            }
            if (!IsTileSolid(xStart + i,y + 1,levelData)){
                return false;
            }
        }
        return true;
    }
//    public static float getXHitBoxNextToWall(Rectangle2D.Float hitbox, float xSpeed, int[][] levelData){
//        int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
//        if (xSpeed > 0){
////        Right
//            int currentPosx = (int)(currentTile * Game.TILES_SIZE);
//            return currentPosx + (int)(Game.TILES_SIZE - hitbox.width - 1);
//        } else {
//            return currentTile * Game.TILES_SIZE;
////        Left
//        }
//    }

//    public static float getPosUnderFloor(Rectangle2D.Float hitbox, float ySpeed, int[][] levelData){
//        int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
//        if (ySpeed > 0){
////        Up
//            int currentPosy = (int)(currentTile * Game.TILES_SIZE);
//            return currentPosy + (int)(Game.TILES_SIZE - hitbox.height - 1);
//        } else {
////            Down
//            return currentTile * Game.TILES_SIZE;
//        }
//    }
    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        if (!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if (!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;
        return true;
    }
    public static boolean IsOnFloor(Rectangle2D.Float hitbox,float xSpeed, int[][] lvlData){
        if (xSpeed > 0)
            return isSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        return isSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
    }
     public static boolean isSightClear(int[][] lvlData,Rectangle2D.Float firstHitBox,Rectangle2D.Float secondHitBox,int yTile,int[][] levelData){
        int firstXTile = (int) (firstHitBox.x/Game.TILES_SIZE);
        int secondXTile = (int) (secondHitBox.x/Game.TILES_SIZE);
        if (firstXTile > secondXTile){
            return IsAllTileWalkable(secondXTile,firstXTile,yTile,levelData);
        } else {
            return IsAllTileWalkable(firstXTile,secondXTile ,yTile,levelData);
        }
     }
}
