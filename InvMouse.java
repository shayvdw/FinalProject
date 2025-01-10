import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InvMouse implements MouseListener, MouseMotionListener {

    boolean clicked;
    int x;
    int y;

    public InvMouse() {
        clicked = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clicked = !clicked;
        e.getX();
        e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
