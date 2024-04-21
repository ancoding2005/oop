import java.util.Scanner;

public class blowup {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String t = "";
        for (int i = 0; i < s.length() - 1;++i){
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9'){
                for (int j = 1; j <= Character.getNumericValue(s.charAt(i)); ++j){
                    t = t + s.charAt(i + 1);
                }
            }
            else{
                t = t + s.charAt(i);
            }
        }
        t = t + s.charAt(s.length() - 1);
        System.out.println(t);
    }
}