import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard {
    public Keyboard() {
        
    }
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_E){
            System.out.println("E");
        }
    }
    
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub
    
    }
}
