import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int Width = 360;
    int Height = 640;

//    Image
    Image background;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

//    Bird
    int xBird = Width/8;
    int yBird = Height/2;
    int birdSizeX = 34;
    int birdSizeY = 24;

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver){
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            velocityY = -10;
            if (gameOver){
                gameOver = false;
                bird.y = yBird;
                velocityY = 0;
                pipes.clear();
                score = 0;
                placePipesTimer.start();
                gameLoop.start();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    //Pipes
    int pipeX = Width;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    //game logic
    Bird bird;
    Pipe pipe;
    int velocityY = -10;
    int gravity = 1;
    int velocityX = -5;

    Timer gameLoop;
    Timer placePipesTimer;
    boolean gameOver = false;
    double score = 0;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    FlappyBird(){
        setPreferredSize(new Dimension(Width,Height));
//        setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);

        //load img
        background = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();

        bird = new Bird(xBird,yBird,birdImg);
        pipes = new ArrayList<Pipe>();

        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });

        placePipesTimer.start();

        gameLoop = new Timer(1000/60,this);
        gameLoop.start();
    }

    public void placePipes(){
        int randomPipeY = (int)(pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int emptySpace = Height/4;

        Pipe topPipe = new Pipe(pipeX,pipeY,pipeWidth,pipeHeight,topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(pipeX,pipeY,pipeWidth,pipeHeight,bottomPipeImg);
        bottomPipe.y = topPipe.y + emptySpace + pipeHeight;
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        g.drawImage(background,0,0,Width,Height,null);

        g.drawImage(bird.img,bird.x,bird.y, birdSizeX, birdSizeY,null);

        for (int i = 0; i < pipes.size();++i) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img,pipe.x,pipe.y,pipe.width,pipe.height,null);
        }

        //score
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN, 32));
        if (gameOver){
            g.drawString("Game Over: " + String.valueOf((int) score),10,35);
        }
        else {
            g.drawString(String.valueOf((int)score),10,35);
        }

    }


    public void move(){
        //Bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(0,bird.y);

        //pipes
        for (Pipe pipe : pipes) {
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width){
                pipe.passed = true;
                score += 0.5;
            }

            //gameOver1
            if (bird.y > Height){
                gameOver = true;
            }
            if (collision(bird,pipe)){
                gameOver = true;
            }
        }


    }

    public boolean collision(Bird a, Pipe b){
        return a.x + birdSizeX > b.x &&
               a.x < b.x + b.width &&
               a.y < b.y + b.height &&
               a.y + birdSizeY > b.y;
    }

}
