/******************************************************************************
 *  Compilation:  javac RandomWalk.java
 *  Execution:    java RandomWalk n
 *  Dependencies: StdDraw.java
 *
 *  % java RandomWalk 20
 *  total steps = 300
 *
 *  % java RandomWalk 50
 *  total steps = 2630
 *
 *  Simulates a 2D random walk and plots the trajectory.
 *
 *  Remarks: works best if n is a divisor of 600.
 *
 ******************************************************************************/

 public class RandomWalk {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        StdDraw.setScale(-n - 0.5, n + 0.5);
        StdDraw.clear(StdDraw.GRAY);
        StdDraw.enableDoubleBuffering();

        int x = 0, y = 0;
        int steps = 0;
        int dx = 1, dy = 0;
        int len = 1;
        int stepsDir = 0; 

        while (Math.abs(x) <= n && Math.abs(y) <= n) {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledSquare(x, y, 0.45);

            x += dx;
            y += dy;
            stepsDir++;

            if (stepsDir == len) {
                stepsDir = 0;
                int temp = dx;
                dx = -dy;
                dy = temp;
                if (dy == 0) {
                    len++;
                }
            }

            steps++;
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.filledSquare(x, y, 0.45);
            StdDraw.show();
            StdDraw.pause(40);
        }
        StdOut.println("Total steps = " + steps);
    }

}

