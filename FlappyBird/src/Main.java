import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        int Width = 360;
        int Height = 640;

        JFrame frame = new JFrame("Flappy Bird");
//        JButton b = new JButton("Click");

//        frame.add(b);
        frame.setSize(Width,Height);
        frame.setLocationRelativeTo(null);

        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        flappyBird.requestFocus();
        frame.setVisible(true);
    }
}