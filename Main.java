import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Screen s = new Screen();
        s.startGameThread();
        Scanner input = new Scanner(System.in);
        String test = "";
        while (!test.equals("done")) {
            test = input.nextLine();
            if (test.equals("s")) {
                s.SmallScreen();
            } else if (test.equals("b")) {
                s.BigScreen();
            }
        }
        input.close();
    }
}