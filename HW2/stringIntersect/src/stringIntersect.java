import java.util.HashSet;
import java.util.Scanner;

public class stringIntersect {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String a = scanner.nextLine();
        String b = scanner.nextLine();
        int len = scanner.nextInt();
        System.out.println(stringIntersect(a,b,len));
    }

    public static boolean stringIntersect(String a, String b, int len) {
        HashSet<String> setA = new HashSet<>();
        for (int i = 0; i <= a.length() - len; ++i){
            setA.add(a.substring(i,i + len));
        }
        for (int i = 0; i <= b.length() - len; ++i){
            if (setA.contains(b.substring(i,i + len))){
                return true;
            }
        }
        return false;
    }
}