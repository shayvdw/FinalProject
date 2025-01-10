import javax.swing.*;
import java.awt.Graphics;
import java.awt.GridLayout;

public class Inventory extends JPanel implements Runnable {
    int width = 500;
    int height = 300;
    JFrame frame = new JFrame();
    
    InventorySquare[][] inventory = new InventorySquare[8][4];
    boolean alreadyHidden = true;
    boolean alreadyShown = false;
    Keyboard k = new Keyboard();
    InvMouse mouse = new InvMouse();

    public Inventory() {
        this.setLayout(new GridLayout(8, 4));
        frame.setLayout(null);
        this.setBounds(0, 0, width, height);
        for (int i = 0; i < inventory.length; i++) {
            for (int j = 0; j < inventory[0].length; j++) {
                inventory[i][j] = new InventorySquare(10 + i * 50, 10 + j * 50);
                this.add(inventory[i][j]);
            }
        }

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.add(this);
        frame.setUndecorated(true);

        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);

        frame.addKeyListener(k);
        frame.addMouseListener(mouse);
        frame.addMouseMotionListener(mouse);
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
            if (k.closeInv && alreadyShown) {
                hide();
                k.closeInv = false;
            } else if (mouse.clicked){
                // System.out.println("Mouse clicked at: " + mouse.x + ", " + mouse.y);
                for (InventorySquare[] inventorySquares : inventory) {
                    for (InventorySquare inventorySquare : inventorySquares) {
                        if (inventorySquare.getSpotX() < mouse.x && inventorySquare.getSpotX() + 40 > mouse.x && inventorySquare.getSpotY() < mouse.y && inventorySquare.getSpotY() + 40 > mouse.y) {
                            System.out.println("Clicked on inventory square at: " + ((inventorySquare.getSpotY()-10)/50) + ", " + ((inventorySquare.getSpotX()-10)/50));
                        }
                    }
                }
                mouse.clicked = false;
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
        for (InventorySquare[] invSpots : inventory) {
            for (InventorySquare invSpot : invSpots) {
                g.fillRect(invSpot.getSpotX(), invSpot.getSpotY(), 40, 40);
            }
        }
    }

}
