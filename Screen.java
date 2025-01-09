import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable {
    int width = 500;
    int height = 500;
    JFrame frame = new JFrame();
    Keyboard k = new Keyboard();
    Inventory i = new Inventory();

    public Screen() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(this);
        frame.addKeyListener(k);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setSize(width, height);

        frame.setVisible(true);

    }

    public void startGameThread() {
        Thread display = new Thread(this);
        display.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (k.forward) {
                System.out.println("Forward");
            } else if (k.backward) {
                System.out.println("Backward");
            } else if (k.left) {
                System.out.println("Left");
            } else if (k.right) {
                System.out.println("Right");
            } else if (k.inv) {
                i.show();
                System.out.println("Inventory is shown");
            } else if (!k.inv) {
                i.hide();
                System.out.println("Inventory is hidden");
            }
        }
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
