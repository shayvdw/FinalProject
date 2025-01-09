import javax.swing.*;
import java.awt.Graphics;

public class Inventory extends JPanel implements Runnable {
    int width = 500;
    int height = 300;
    JFrame frame = new JFrame();
    int[][] inventory;
    boolean alreadyHidden;
    boolean alreadyShown;
    Keyboard k = new Keyboard();
    Mouse m = new Mouse();
    

    public Inventory() {
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.add(this);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(false);
        frame.addKeyListener(k);
        frame.addMouseListener(m);

        inventory = new int[4][8];
        alreadyHidden = true;
        alreadyShown = false;

    }
    public void startInventoryThread() {
        Thread inventoryThread = new Thread(this);
        inventoryThread.start();
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // System.out.println("x: " + m.x + " y: " + m.y);
            if (k.closeInv && alreadyShown) {
                hide();
                k.closeInv = false;
            }
        }
    }

    public void hide() {
        if (!alreadyHidden) {
            frame.setVisible(false);
            alreadyHidden = true;
            alreadyShown = false;
        }
    }

    public void show() {
        if (!alreadyShown) {
            frame.setVisible(true);
            alreadyHidden = false;
            alreadyShown = true;
        }
    }

    public void paintComponent(Graphics g) {
        for (int i = 0; i < inventory[0].length; i++) {
            for (int j = 0; j < inventory.length; j++) {
                g.fillRect(10 + i * 50, 10 + j * 50, 40, 40);
            }
        }
    }

}
