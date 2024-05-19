package gamestates;

import Levels.LevelManager;
import MyFlatFormer.Game;
import MyFlatFormer.SoundPlayer;
import UI.GameOver;
import UI.GameWin;
import UI.PausePlayer;
import entities.EnemyManager;
import entities.Player;
import utilz.Load;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Playing extends State implements Statemethods{
    private Player player;
    private LevelManager levelManager;
    private boolean paused = false;
    private PausePlayer pausePlayer;
    private BufferedImage backgroungImg;
    private EnemyManager enemyManager;

    private int xlvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int lvlWidth = Load.GetLevelData()[0].length;
    private int maxTileOffset = lvlWidth - Game.TILES_WIDTH;
    private int maxlvlOffset = (int) (maxTileOffset * Game.TILES_SIZE);

    private boolean isgameOver;
    private GameOver gameOver;
    private boolean isWin;
    private GameWin gameWin;
    private SoundPlayer soundBackground;
    private SoundPlayer soundInGame;
    private SoundPlayer soundDead;
    private SoundPlayer soundWin;

    public Playing(Game game) {
        super(game);
        initClasses();
        loadBackground();
        soundBackground.play();
    }

    private void loadBackground() {
        backgroungImg = Load.getImgAtlas(Load.BACKGROUND);
    }

    private void initClasses() {
        soundBackground = new SoundPlayer("D:\\OOP\\oop\\MyGameWithJava\\MyFirstFlatFormer\\src\\Resource\\audio\\menu.wav");
        soundInGame = new SoundPlayer("D:\\OOP\\oop\\MyGameWithJava\\MyFirstFlatFormer\\src\\Resource\\audio\\level1.wav");
        soundDead = new SoundPlayer("D:\\OOP\\oop\\MyGameWithJava\\MyFirstFlatFormer\\src\\Resource\\audio\\die.wav");
        soundWin = new SoundPlayer("D:\\OOP\\oop\\MyGameWithJava\\MyFirstFlatFormer\\src\\Resource\\audio\\lvlcompleted.wav");
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        player = new Player(100, 350, (int) (74 * Game.SCALE), (int) (50 * Game.SCALE),this);
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        pausePlayer = new PausePlayer(this);
        gameOver = new GameOver(this);
        gameWin = new GameWin(this);
    }

    @Override
    public void update() {
        if (!paused && !isgameOver && !isWin) {
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(),player);
            checkBorder();
        } else {
            pausePlayer.update();
        }
    }

    private void checkBorder() {
        int xPlayer = (int) player.getHitBox().x;
        int diff = xPlayer - xlvlOffset;

        if (diff > rightBorder){
            xlvlOffset += diff - rightBorder;
        } else if (diff < leftBorder){
            xlvlOffset += diff - leftBorder;
        }
        if (xlvlOffset > maxlvlOffset){
            xlvlOffset = maxlvlOffset;
        }
        else if (xlvlOffset < 0){
            xlvlOffset = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroungImg,0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT,null);
        levelManager.draw(g,xlvlOffset);
        player.render(g,xlvlOffset);
        enemyManager.draw(g,xlvlOffset);
        if (paused){
            g.setColor(new Color(0,0,0,100));
            g.fillRect(0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT);
            pausePlayer.draw(g);
        } else if (isgameOver){
            gameOver.draw(g);
        } else if (isWin){
            gameWin.draw(g);
        }
    }

    public void resetAll(){
        isgameOver = false;
        isWin = false;
        paused = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox){
        enemyManager.checkEnemyHit(attackBox);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1){
//            player.setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!isgameOver && !isWin)
            if (paused){
                pausePlayer.mousePressed(e);
            }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!isgameOver && !isWin)
            if (paused){
                pausePlayer.mouseReleased(e);
            }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!isgameOver && !isWin)
            if (paused){
                pausePlayer.mouseMoved(e);
            }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isWin) {
            gameWin.keyPressed(e);
        } else if (isgameOver) {
            gameOver.keyPressed(e);
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_E:
                    player.setAttacking(true);
                    break;
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!isgameOver && !isWin) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
        }
    }
    public Player getPlayer(){
        return player;
    }

    public void setGameOver(boolean isgameOver) {
        this.isgameOver = isgameOver;
    }

    public void setGameWin(boolean isWin) {
        this.isWin = isWin;
    }
    public SoundPlayer getSoundBackground(){
        return soundBackground;
    }
    public SoundPlayer getSoundInGame(){
        return soundInGame;
    }
    public SoundPlayer getSoundDead(){
        return soundDead;
    }
    public SoundPlayer getSoundWin(){
        return soundWin;
    }
}
