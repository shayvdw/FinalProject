import java.util.Scanner;

public class Controller {
    public Controller() {
        Keyboard k = new Keyboard();
        Screen s = new Screen();
        Inventory i = new Inventory();
        Scanner input = new Scanner(System.in);
        String test = "";
        while (!test.equals("done")) {
            test = input.nextLine();
            if (test.equals("s")) {
                s.SmallScreen();
            } else if (test.equals("b")) {
                s.BigScreen();
            } else if(test.equals("inv")){
                i.show();
            } else if(test.equals("hide")){
                i.hide();
            }
        }
        input.close();
    }
}
