import java.util.Scanner;

public class maxRun {
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int count = 1;
        int ans = 0;
        for (int i = 0; i < s.length() - 1; ++i){
            if (s.charAt(i) == s.charAt(i + 1)) count ++;
            else{
                ans = Math.max(ans,count);
                count = 1;
            }
        }
        System.out.println(ans);
    }
}