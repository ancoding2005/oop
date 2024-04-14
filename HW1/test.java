package HW1;
public class test {
    public static void main(String[] args){
        String hoTen = "Nguyen Truong An";
        String maSV = "23020323";
        String lop = "K68AI1";
        String username = "ancoing2005";
        String email = "ancodingai@gmail.com";
        System.out.println(hoTen + "\t" + maSV + "\t" + lop + "\t" + username + "\t" + email);

        for (int i = 9; i >= 1; i--) {
            if (i > 1) {
                System.out.println(i + " bottles of beer on the wall, " + i + " bottles of beer.");
                System.out.println("Take one down, pass it around,");
            } else {
                System.out.println("1 bottle of beer on the wall, 1 bottle of beer.");
                System.out.println("Take one down, pass it around,");
            }
        }
        System.out.println("No more bottles of beer on the wall.");
    }        
}
