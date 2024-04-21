import java.util.*;

public class ShapeClient {
    public static void main(String[] args) {
        Point.Shape a = new Point.Shape("0 0 0 1 1 1 1 0");
        Point.Shape b = new Point.Shape("10 10 10 11 11 11 11 10");
        Point.Shape c = new Point.Shape("0.5 0.5 0.5 -10 1.5 0");
        Point.Shape d = new Point.Shape("0.5 0.5 0.75 0.75 0.75 0.2");

        System.out.println("a crosses b: " + a.cross(b));
        System.out.println("a crosses c: " + a.cross(c));
        System.out.println("a crosses d: " + a.cross(d));
        System.out.println("a encircles b: " + a.encircles(b));
        System.out.println("a encircles c: " + a.encircles(c));
        System.out.println("a encircles d: " + a.encircles(d));
    }
}

