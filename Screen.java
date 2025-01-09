import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Screen extends JPanel {
    int width = 500;
    int height = 500;
    JFrame frame = new JFrame();
    Keyboard k = new Keyboard(); 

    public Screen() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(this);
        frame.addKeyListener(k);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setSize(width, height);

        frame.setVisible(true);

    }

    public void BigScreen() {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void SmallScreen() {
            frame.setSize(width, height);
    }

    public void paintComponent(Graphics g) {
        g.fillRect(10, 10, 100, 100);
    }
}
