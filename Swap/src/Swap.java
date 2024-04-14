import java.util.Random;

public class Swap {
    public static void main(String[] args) {
        Random rand = new Random();
        int a = rand.nextInt();
        int b = rand.nextInt();
        int c = 0;
        System.out.println("a = " + a + ", b = " + b + ", c = " + c);
        c = a;
        System.out.println("a = " + a + ", b = " + b + ", c = " + c);
        a = b;
        System.out.println("a = " + a + ", b = " + b + ", c = " + c);
        b = c;
        System.out.println("a = " + a + ", b = " + b + ", c = " + c);
    }

}