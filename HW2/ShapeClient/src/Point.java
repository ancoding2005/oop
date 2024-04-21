import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Point extends ShapeClient{
    double x,y;

    public Point(double x,double y){
        this.x = x;
        this.y = y;
    }
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }
    public int hashCode(){
        return Objects.hash(x, y);
    }
    static class Shape {
        private List<Point> vertices;

        public Shape(String shapeS) {
            vertices = new ArrayList<>();
            try (Scanner scanner = new Scanner(shapeS)) {
                while (scanner.hasNextDouble()) {
                    double x = scanner.nextDouble();
                    double y = scanner.nextDouble();
                    vertices.add(new Point(x, y));
                }
            }
        }

        private Point calculateCenter() {
            double sumX = 0, sumY = 0;
            for (Point vertex : vertices) {
                sumX += vertex.x;
                sumY += vertex.y;
            }
            return new Point(sumX / vertices.size(), sumY / vertices.size());
        }

        private double calculateRadius(Point center) {
            double ans = Double.POSITIVE_INFINITY;;
            for (Point vertex : vertices) {
                double distance = Math.sqrt(Math.pow(vertex.x - center.x, 2) + Math.pow(vertex.y - center.y, 2));
                ans = Math.min(ans, distance);
            }
            return ans;
        }

        public boolean cross(Shape B) {
            Point centerA = calculateCenter();
            Point centerB = B.calculateCenter();
            double radiusA = calculateRadius(centerA);
            double radiusB = B.calculateRadius(centerB);
            double distance = Math.sqrt(Math.pow(centerB.x - centerA.x, 2) + Math.pow(centerB.y - centerA.y, 2));
            return distance < radiusA + radiusB && distance + radiusB >= radiusA;
        }

        public int encircles(Shape O) {
            Point centerA = calculateCenter();
            Point centerB = O.calculateCenter();
            double radiusA = calculateRadius(centerA);
            double radiusB = O.calculateRadius(centerB);
            double distance = Math.sqrt(Math.pow(centerB.x - centerA.x, 2) + Math.pow(centerB.y - centerA.y, 2));
            if (distance + radiusB < radiusA) return 2;
            if (distance < radiusA + radiusB) return 1;
            return 0;
        }
    }
}
