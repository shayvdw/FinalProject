
/*
 * the inventrory class
 * runs on a seperate thread
 * allows for items to be moved around in the inventory
 * saves item to file after closing inventory
 * connected to the screen class
 */
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.PrintWriter;

public class Inventory extends JPanel implements Runnable {
    int width = 450;
    int height = 250;

    JFrame frame = new JFrame();

    InventorySquare[][] inventory;
    InventorySquare foundSquare;

    Item heldItem;
    Item drop = null;

    boolean alreadyHidden = true;
    boolean alreadyShown = false;
    boolean hasFound = false;

    Keyboard k = new Keyboard();
    Mouse mouse = new Mouse();

    // constructor for the inventory
    // connects the inventory array to the screen
    public Inventory(InventorySquare[][] a) {
        this.setLayout(new GridLayout(8, 4));
        frame.setLayout(null);
        this.setBounds(0, 0, width, height);
        inventory = a;

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

    // starts the inventory thread
    public void startInventoryThread() {
        Thread inventoryThread = new Thread(this);
        inventoryThread.start();
    }

    public void run() {
        while (true) {
            try {
                // 60 fps so my computer doesn't explode
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // allows inventory to close itself
            if (k.closeInv && alreadyShown) {
                hide();
                k.closeInv = false;
            }
            // when the mouse is clicked finds the square that was clicked if one was clicked
            if (mouse.clicked && !hasFound) {
                foundSquare = findSquare(mouse.x, mouse.y);
                if (foundSquare != null && heldItem == null) {
                    heldItem = foundSquare.getItem();
                    foundSquare.setItem(null);
                    heldItem.clicked();
                    heldItem.setSquare(null);
                    hasFound = true;
                }else if(foundSquare != null){
                    Item temp = foundSquare.getItem();
                    heldItem.clicked();
                    heldItem.setSquare(foundSquare);
                    foundSquare.setItem(heldItem);
                    foundSquare.getItem().updateItem();
                    heldItem = temp;
                    heldItem.clicked();
                    temp = null;
                } else if(heldItem != null && foundSquare == null){
                    heldItem.clicked();
                    heldItem.hold();
                    drop = heldItem;
                    heldItem = null;
                }
                mouse.clicked = false;
            }
            // transfers held item to new square if one is clicked else send back to
            // original square
            else if (mouse.clicked && hasFound && heldItem != null) {
                InventorySquare transferSquare = findSquare(mouse.x, mouse.y);
                if (transferSquare != null && transferSquare != foundSquare) {
                    Item temp = transferSquare.getItem();
                    heldItem.clicked();
                    heldItem.setSquare(transferSquare);
                    transferSquare.setItem(heldItem);
                    temp.setSquare(foundSquare);
                    temp.updateItem();
                    heldItem.updateItem();
                    foundSquare.setItem(temp);
                    heldItem = null;
                    foundSquare = null;
                } else{
                    heldItem.clicked();
                    heldItem.setSquare(null);
                    heldItem.hold();
                    drop = heldItem;
                    foundSquare.setItem(null);
                    heldItem = null;
                }
                hasFound = false;
                mouse.clicked = false;
                repaint();
            }
            repaint();
        }
    }

    // hides the inventory
    public void hide() {
        if (!alreadyHidden) {
            frame.setVisible(false);
            alreadyHidden = true;
            alreadyShown = false;
            saveInventory();
        }
    }

    // shows the inventory
    public void show() {
        if (!alreadyShown) {
            frame.setVisible(true);
            alreadyHidden = false;
            alreadyShown = true;
        }
    }

    // paints the inventory
    public void paintComponent(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        for (InventorySquare[] invSpots : inventory) {
            for (InventorySquare invSpot : invSpots) {
                g.fillRect(invSpot.getSpotX(), invSpot.getSpotY(), 40, 40);
            }
        }
        for (InventorySquare[] invSpots : inventory) {
            for (InventorySquare invSpot : invSpots) {
                if (invSpot.getItem() != null && invSpot.getItem() != heldItem) {
                    invSpot.getItem().drawItem(g, invSpot.getSpotX(), invSpot.getSpotY());
                }
            }
        }
        if (heldItem != null) {
            heldItem.drawItem(g, mouse.x, mouse.y);
        }
    }

    public void pickUp(Item item){
        this.show();
        item.setSquare(null);
        heldItem = item;
        heldItem.clicked();
        repaint();
    }

    // finds a square given x and y cordinates
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

    // saves all inventory data to a file
    public void saveInventory() {
        PrintWriter output = null;
        try {
            output = new PrintWriter("saveFile.csv");
            output.println("SpotX,SpotY,ItemID,ItemWidth,ItemHeight,hasItem");
            for (InventorySquare[] invSpots : inventory) {
                for (InventorySquare invSpot : invSpots) {
                    if (invSpot.getItem() != null) {
                        output.println(invSpot.getSpotX() + "," + invSpot.getSpotY() + ","
                                + invSpot.getItem().getItemID()
                                + "," + invSpot.getItem().getItemWidth() + ","
                                + invSpot.getItem().getItemHeight() + "," + invSpot.getItem().isHeld());
                    } else {
                        output.println(invSpot.getSpotX() + "," + invSpot.getSpotY() + "," + 0
                                + "," + 0 + "," + 0 + "," + null);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        output.close();
    }

    public Item droppedItem() {
        return drop;
    }

    public void deleteItem() {
        drop = null;
    }
}
