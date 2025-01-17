import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable {
    int width = 500;
    int height = 500;

    JFrame frame = new JFrame();

    Keyboard k = new Keyboard();
    MapMouse mouse = new MapMouse();

    Inventory i = new Inventory();

    Player player;

    int playerSpeed = 5;

    public Screen() {
        player = new Player(width / 2, height / 2);
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
                player.move(0, -playerSpeed);
            }
            if (k.backward) {
                player.move(0, playerSpeed);
            }
            if (k.left) {
                player.move(-playerSpeed, 0);
            }
            if (k.right) {
                player.move(playerSpeed, 0);
            }
            if (k.inv) {
                if (i.alreadyHidden) {
                    i.show();
                } else {
                    i.hide();
                }
                k.inv = false;
            }
            if (k.fullScreen) {
                if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                    SmallScreen();
                } else {
                    BigScreen();
                }
                k.fullScreen = false;
            }
            repaint();
            k.forward = false;
            k.backward = false;
            k.left = false;
            k.right = false;
        }
    }

    public void BigScreen() {
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void SmallScreen() {
        frame.setSize(width, height);
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 10000, 10000);
        player.drawPlayer(g);
    }

}
