package utilz;

import MyFlatFormer.Game;

public class Constants {

    public static class EnemyConstants{
        public static final int ENEMY1 = 0;
        public static final int ENEMY2 = 5;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int ENEMY1_WIDTH_DEFAULT = 72;
        public static final int ENEMY1_HEIGHT_DEFAULT = 32;
        public static final int ENEMY1_WIDTH = (int) (ENEMY1_WIDTH_DEFAULT * Game.SCALE);
        public static final int ENEMY1_HEIGHT = (int) (ENEMY1_HEIGHT_DEFAULT * Game.SCALE);

        public static final int ENEMY2_WIDTH_DEFAULT = 128;
        public static final int ENEMY2_HEIGHT_DEFAULT = 128;
        public static final int ENEMY2_WIDTH = (int) (ENEMY2_WIDTH_DEFAULT/2 * Game.SCALE);
        public static final int ENEMY2_HEIGHT = (int) (ENEMY2_HEIGHT_DEFAULT/2 * Game.SCALE);

        public static final int ENEMY1_OFFSET_X = (int) (26 * Game.SCALE);
        public static final int ENEMY1_OFFSET_Y = (int) (9 * Game.SCALE);

        public static final int ENEMY2_OFFSET_X = (int) (28 * Game.SCALE);
        public static final int ENEMY2_OFFSET_Y = (int) (32 * Game.SCALE);

        public static final int LIGHTING_BALL_DEFAULT_WIDTH = 40;
        public static final int LIGHTING_BALL_DEFAULT_HEIGHT = 40;
        public static final int LIGHTING_BALL_WIDTH = (int) (LIGHTING_BALL_DEFAULT_WIDTH * Game.SCALE);
        public static final int LIGHTING_BALL_HEIGHT = (int) (LIGHTING_BALL_DEFAULT_HEIGHT * Game.SCALE);
        public static final float SPEED = 0.5f * Game.SCALE;
        public static int dir = 1;


        public static int getSpriteAmount(int enemyType, int enemyState){
            switch (enemyType){
                case ENEMY1:
                    switch (enemyState){
                        case IDLE:
                            return 9;
                        case RUNNING:
                            return 6;
                        case HIT:
                            return 4;
                        case DEAD:
                            return 5;
                        case ATTACK:
                            return 7;
                    }
                case ENEMY2:
                    switch (enemyState){
                        case IDLE:
                            return 7;
                        case RUNNING:
                            return 8;
                        case HIT:
                            return 3;
                        case DEAD:
                            return 5;
                        case ATTACK:
                            return 7;
                    }
            }
            return 0;
        }

        public static int getMaxHealth(int enemy_type){
            switch (enemy_type){
                case ENEMY1:
                    return 19;
                case ENEMY2:
                    return 7;
                default:
                    return 1;
            }
        }

        public static int getEnemyDmg(int enemyType){
            switch (enemyType){
                case ENEMY1:
                    return 15;
                default:
                    return 0;
            }
        }
    }

    public static class UI{
        public static class Button{
            public static int ButtonWidthDefault = 140;
            public static int ButtonHeightDefault = 56;
            public static int ButtonWidth = (int) (ButtonWidthDefault * Game.SCALE);
            public static int ButtonHeight = (int) (ButtonHeightDefault * Game.SCALE);
        }
        public static class PauseButtons{
            public static int SoundSizeDefault = 42;
            public static int SoundSize = (int) (SoundSizeDefault * Game.SCALE);
        }
    }

    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants{
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int DEAD = 4;
        public static final int HIT = 5;
        public static final int ATTACK_1 = 6;
        public static final int ATTACK_JUMP_1 = 7;
        public static final int ATTACK_JUMP_2 = 8;

        public static int getAniAmount(int player_action){
            switch (player_action){
                case IDLE:
                    return 7;
                case RUNNING:
                    return 8;
                case HIT:
                    return 3;
                case JUMP:
                    return 5;
                case ATTACK_1:
                case ATTACK_JUMP_1:
                case FALLING:
                    return 4;
                case ATTACK_JUMP_2:
                    return 14;
                case DEAD:
                    return 6;
                default:
                    return 1;
            }
        }
    }
}
