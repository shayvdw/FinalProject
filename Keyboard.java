import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    boolean closeInv;
    boolean forward;
    boolean backward;
    boolean left;
    boolean right;
    boolean inv;
    boolean fullScreen;

    public Keyboard() {
        closeInv = false;
        forward = false;
        backward = false;
        left = false;   
        right = false;
        inv = false;
        fullScreen = false;

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'w'){
            this.forward = !forward;
        } else if(e.getKeyChar() == 's'){
            this.backward = !backward;
        } else if(e.getKeyChar() == 'a'){
            this.left = !left;
        } else if(e.getKeyChar() == 'd'){
            this.right = !right;
        } else if(e.getKeyChar() == 'i'){
            this.inv = !inv;
        } else if(e.getKeyChar() == 'e'){
            this.closeInv = !closeInv;
        } else if(e.getKeyChar() == 'q'){
            System.exit(0);
        } else if(e.getKeyChar() == 'f'){
            this.fullScreen = !fullScreen;
        }
    }

   
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_E){
            System.out.println("E");
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // This method must be overridden but can be left empty if not used
    }
}
