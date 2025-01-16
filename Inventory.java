import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

public class Inventory extends JPanel implements Runnable {
    int width = 450;
    int height = 250;

    JFrame frame = new JFrame();

    InventorySquare[][] inventory = new InventorySquare[8][4];
    InventorySquare foundSquare;

    Item heldItem;

    boolean alreadyHidden = true;
    boolean alreadyShown = false;
    boolean hasFound = false;

    Keyboard k = new Keyboard();
    InvMouse mouse = new InvMouse();

    public Inventory() {
        this.setLayout(new GridLayout(8, 4));
        frame.setLayout(null);
        this.setBounds(0, 0, width, height);
        for (int i = 0; i < inventory.length; i++) {
            for (int j = 0; j < inventory[0].length; j++) {
                inventory[i][j] = new InventorySquare(10 + i * (width / inventory.length),
                        10 + j * (height / inventory[0].length));
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
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (k.closeInv && alreadyShown) {
                hide();
                k.closeInv = false;
            } else if (mouse.clicked && !hasFound) {
                foundSquare = findSquare(mouse.x, mouse.y);
                if (foundSquare != null) {
                    foundSquare.clicked();
                    foundSquare.sprite.clicked();
                    hasFound = true;
                    mouse.clicked = false;
                }
            } else if (mouse.clicked && hasFound) {
                foundSquare.clicked();
                foundSquare.sprite.clicked();
                foundSquare.sprite.updateItem();
                hasFound = false;
                mouse.clicked = false;
                repaint();
            } else if (hasFound) {
                // foundSquare.setSpotY(mouse.y - 20);
                // foundSquare.setSpotX(mouse.x - 20);
                repaint();
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
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        for (InventorySquare[] invSpots : inventory) {
            for (InventorySquare invSpot : invSpots) {
                if (invSpot.isGrabbed()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(invSpot.getSpotX(), invSpot.getSpotY(), 40, 40);
                invSpot.sprite.drawItem(g, mouse.x, mouse.y);
            }
        }
    }

    public InventorySquare findSquare(int x, int y) {
        for (InventorySquare[] invSpots : inventory) {
            for (InventorySquare invSpot : invSpots) {
                if (x > invSpot.getSpotX() && x < invSpot.getSpotX() + 40 && y > invSpot.getSpotY()
                        && y < invSpot.getSpotY() + 40) {
                    return invSpot;
                }
            }
        }
        return null;
    }

}
