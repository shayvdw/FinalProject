import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable {
    int width = 500;
    int height = 500;
    JFrame frame = new JFrame();
    Keyboard k = new Keyboard();
    Inventory i = new Inventory();
    MapMouse mouse = new MapMouse();

    public Screen() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);

        frame.add(this);
        frame.addKeyListener(k);
        frame.addMouseListener(mouse);
        frame.addMouseMotionListener(mouse);

        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setSize(width, height);
        frame.setVisible(true);

    }

    public void startGameThread() {
        Thread display = new Thread(this);
        display.start();
        i.startInventoryThread();
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
                // System.out.println("Forward");
                k.forward = false;
            } else if (k.backward) {
                // System.out.println("Backward");
                k.backward = false;
            } else if (k.left) {
                // System.out.println("Left");
                k.left = false;
            } else if (k.right) {
                // System.out.println("Right");
                k.right = false;
            } else if (k.inv) {
                if (i.alreadyHidden) {
                    i.show();
                }else {
                    i.hide();
                }
                k.inv = false;
            } else if (k.fullScreen) {
                if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                    SmallScreen();
                } else {
                    BigScreen();
                }
                k.fullScreen = false;
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
