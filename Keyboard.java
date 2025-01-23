/*
 * handles keyboard input
 * uses booleans as the changes are applied in the game loop
 */
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
    boolean save;

    public Keyboard() {
        save = false;
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
        }
        if(e.getKeyChar() == 's'){
            this.backward = !backward;
        }
        if(e.getKeyChar() == 'a'){
            this.left = !left;
        }
        if(e.getKeyChar() == 'd'){
            this.right = !right;
        }
        if(e.getKeyChar() == 'i'){
            this.inv = !inv;
        }
        if(e.getKeyChar() == 'e'){
            this.closeInv = !closeInv;
        }
        if(e.getKeyChar() == 'q'){
            System.exit(0);
        }
        if(e.getKeyChar() == 'f'){
            this.fullScreen = !fullScreen;
        }
        if(e.getKeyChar() == 'o'){
            this.save = true;
        }
    }

   
    @Override
    public void keyReleased(KeyEvent e) {
        // This method must be overridden but can be left empty if not used
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // This method must be overridden but can be left empty if not used
    }
}
