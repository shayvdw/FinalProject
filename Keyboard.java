import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    public Keyboard() {
        
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_E){
            System.out.println("E");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // This method must be overridden but can be left empty if not used
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_E){
            System.out.println("E");
        }
    }
}
