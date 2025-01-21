/*
 * @author: Shay van der Watt
 * runs the "game"
 */
public class Main {
    public static void main(String[] args) {
        Screen s = new Screen();
        //starts the thread that runs screen
        s.startGameThread();
    }
}