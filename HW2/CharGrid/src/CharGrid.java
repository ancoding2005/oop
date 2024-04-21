import java.util.Scanner;

public class CharGrid {
    private static char[][] table;
    public CharGrid(char[][] table){
        this.table = table;
    }
    public static void main(String[] args) {
//            Scanner scanner = new Scanner(System.in);
//            int rows = scanner.nextInt();
//            int cols = scanner.nextInt();
//            scanner.nextLine();
//            table = new char[rows][cols];
//            for (int i = 0; i < rows; ++i){
//                String line = scanner.nextLine();
//                table[i] = line.toCharArray();
//            }
            int rows = 7;
            int cols = 9;
//            scanner.nextLine();
            char[][] inputTable = new char[rows][cols];
            inputTable[0] = "  p".toCharArray();
            inputTable[1] = "  p    x".toCharArray();
            inputTable[2] = "ppppp xxx".toCharArray();
            inputTable[3] = "  p  y x".toCharArray();
            inputTable[4] = "  p yyy".toCharArray();
            inputTable[5] = "zzzzzyzzz".toCharArray();
            inputTable[6] = "  xx y".toCharArray();
            CharGrid charGrid = new CharGrid(inputTable);
//            char ch = scanner.next().charAt(0);
            char ch = 'p';
            System.out.println(ch + " " + charArea(ch));
            System.out.println(countPlus());

    }
    public static int charArea(char ch) {
        int minRow = Integer.MAX_VALUE;
        int minCol = Integer.MAX_VALUE;
        int maxRow = Integer.MIN_VALUE;
        int maxCol = Integer.MIN_VALUE;
        boolean kt = false;
        for (int i = 0; i < table.length; ++i){
            for (int j = 0; j < table[i].length; ++j) {
                if (table[i][j] == ch) {
                    kt = true;
                    minRow = Math.min(minRow, i);
                    minCol = Math.min(minCol, j);
                    maxRow = Math.max(maxRow, i);
                    maxCol = Math.max(maxCol, j);
                }
            }
        }
        if (!kt){
            return 0;
        }
        int length = maxCol - minCol + 1;
        int width = maxRow - minRow + 1;
        return length * width;
    }
    private static int countPlus(){
        int count = 0;
        for (int i = 0; i < table.length; ++i){
            for (int j = 0; j < table[i].length; ++j){
                if (table[i][j] != ' ' && check(i,j)){
                    count ++;
                }

            }
        }
        return count;
    }

    private static boolean check(int i, int j) {
        if (isValid(i - 1,j,-1,0) && isValid(i + 1,j,1,0) && isValid(i,j - 1,0,-1) && isValid(i,j + 1,0,1)){
            return true;
        }
        return false;
    }

    private static boolean isValid(int i, int j, int i1, int i2) {
        if (!(i >= 0 && i < table.length && j >= 0 && j < table[i].length)) return false;
        char center = table[i][j];
        int len = 0;
        while (i >= 0 && i < table.length && j >= 0 && j < table[i].length && table[i][j] == center){
            len ++;
            i += i1;
            j += i2;
        }
        return len >= 2;
    }
}