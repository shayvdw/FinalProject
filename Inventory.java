import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

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
        Scanner input = null;
        try{
            input = new Scanner(new File("SaveFile.csv"));
            input.nextLine();
        } catch(Exception e){
            e.printStackTrace();
        }
        for (int i = 0; i < inventory.length; i++) {
            for (int j = 0; j < inventory[0].length; j++) {
                String dataline = input.nextLine();
                String[] data = dataline.split(",");
                int xPos = Integer.parseInt(data[0]);
                int yPos = Integer.parseInt(data[1]);
                int id = Integer.parseInt(data[2]);
                int width = Integer.parseInt(data[3]);
                int height = Integer.parseInt(data[4]);
                inventory[i][j] = new InventorySquare(xPos,yPos,id,width,height);
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
                    heldItem = foundSquare.sprite;
                    foundSquare.setItem(null);
                    foundSquare.sprite = null;
                    heldItem.clicked();
                    heldItem.setSquare(null);
                    hasFound = true;
                    mouse.clicked = false;
                }
            } else if (mouse.clicked && hasFound) {
                InventorySquare transferSquare = findSquare(mouse.x, mouse.y);
                if (transferSquare != null && transferSquare != foundSquare) {
                    Item temp = transferSquare.sprite;
                    heldItem.clicked();
                    heldItem.setSquare(transferSquare);
                    transferSquare.setItem(heldItem);
                    temp.setSquare(foundSquare);
                    temp.updateItem();
                    heldItem.updateItem();
                    foundSquare.setItem(temp);
                    heldItem = null;
                    foundSquare = null;
                } else {
                    heldItem.clicked();
                    heldItem.setSquare(foundSquare);
                    heldItem.updateItem();
                    foundSquare.setItem(heldItem);
                    heldItem = null;
                }
                hasFound = false;
                mouse.clicked = false;
                repaint();
            } else if (hasFound) {
                repaint();
            }

        }
    }

    public void hide() {
        if (!alreadyHidden) {
            frame.setVisible(false);
            alreadyHidden = true;
            alreadyShown = false;
            saveInventory();
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
            }
        }
        for (InventorySquare[] invSpots : inventory) {
            for (InventorySquare invSpot : invSpots) {
                if (invSpot.sprite != null) {
                    invSpot.sprite.drawItem(g, mouse.x, mouse.y);
                }
                if (invSpot.sprite != null && invSpot.sprite != heldItem) {
                    invSpot.sprite.drawItem(g, invSpot.getSpotX(), invSpot.getSpotY());
                }
            }
        }
        if (heldItem != null) {
            heldItem.drawItem(g, mouse.x, mouse.y);
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

    public void saveInventory() {
        PrintWriter output = null;
        try {
            output = new PrintWriter("saveFile.csv");
            output.println("SpotX,SpotY,ItemID,ItemWidth,ItemHeight");
            for (InventorySquare[] invSpots : inventory) {
                for (InventorySquare invSpot : invSpots) {
                    if (invSpot.sprite != null) {
                        output.println(invSpot.getSpotX() + "," + invSpot.getSpotY() + "," + invSpot.sprite.getItemID()
                         + "," + invSpot.sprite.itemWidth + "," + invSpot.sprite.itemHeight);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        output.close();
    }

}
