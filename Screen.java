
/*
 * is the main game loop
 * uses thread so mutiple parts of game can run at once
 */
import java.awt.*;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable {
    int width = 500;
    int height = 500;

    MapTile[][] map;

    JFrame frame = new JFrame();

    Keyboard k = new Keyboard();
    Mouse mouse = new Mouse();

    Inventory i;

    InventorySquare[][] inventory = new InventorySquare[8][4];

    Player player;

    int playerSpeed = 5;

    public Screen() {
        // reads in save file to make inventory
        Scanner input = null;
        try {
            input = new Scanner(new File("SaveFile.csv"));
            input.nextLine();
        } catch (Exception e) {
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
                boolean hasItem = Boolean.parseBoolean(data[5]);
                inventory[i][j] = new InventorySquare(hasItem, xPos, yPos, xPos / 50, yPos / 50, id, width, height);
                this.add(inventory[i][j]);
            }
        }
        map = new MapTile[35][45];
        try {
            input = new Scanner(new File("MapFile.csv"));
            input.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                String dataline = input.nextLine();
                String[] data = dataline.split(",");
                int xPos = Integer.parseInt(data[0]);
                int yPos = Integer.parseInt(data[1]);
                int id = Integer.parseInt(data[2]);
                int width = Integer.parseInt(data[3]);
                int height = Integer.parseInt(data[4]);
                boolean hasItem = Boolean.parseBoolean(data[5]);
                map[i][j] = new MapTile(hasItem, yPos, xPos, xPos / 50, yPos / 50, width, height, id);
                this.add(map[i][j]);
            }
        }

        i = new Inventory(inventory);

        player = new Player(width / 2, height / 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setUndecorated(true);
        // frame.setLocationRelativeTo(null);

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
            if (k.save) {
                saveFile();
                k.save = false;
            }
            int gridX = player.getX() / 40;
            int gridY = player.getY() / 40;
            // System.out.println(gridX + ", " + gridY);
            // Search 3x3 grid of map tiles around gridX and gridY
            if (i.droppedItem() != null && !i.droppedItem().isHeld()) {
                map[gridX][gridY].setItem(i.droppedItem());
                i.droppedItem().setSquare(map[gridX][gridY]);
                i.droppedItem().updateItem();
                i.droppedItem().hold();
                i.deleteItem();
            }
            if(mouse.clicked){
                MapTile temp = findMapTile(mouse.x, mouse.y);
                if(temp.getItem() != null){
                    i.pickUp(temp.getItem());
                    temp.setItem(null);
                }
                mouse.clicked = false;
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
        g.clearRect(0, 0, 4000, 4000);
        for (MapTile[] MapTiles : map) {
            for (MapTile tile : MapTiles) {
                tile.drawSquare(g);
            }
        }
        for (MapTile[] MapTiles : map) {
            for (MapTile tile : MapTiles) {
                if (tile.getItem() != null) {
                    tile.getItem().drawItem(g, 0, 0);
                }
            }
        }
        player.drawPlayer(g);
    }

    public void saveFile() {
        PrintWriter output = null;
        try {
            output = new PrintWriter("MapFile.csv");
            output.println("SpotX,SpotY,ItemID,ItemWidth,ItemHeight,hasItem");
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    MapTile tile = map[i][j];
                    if (tile.getItem() != null) {
                        output.println(tile.getSpotX() + "," + tile.getSpotY() + ","
                                + tile.getItem().getItemID()
                                + "," + tile.getItem().getItemWidth() + ","
                                + tile.getItem().getItemHeight() + "," + tile.getItem().isHeld());
                    } else {
                        output.println(tile.getSpotX() + "," + tile.getSpotY() + "," + 0
                                + "," + 0 + "," + 0 + "," + null);

                    }
                }
            }
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public MapTile findMapTile(int x, int y) {
        for (MapTile[] tiles : map) {
            for (MapTile tile : tiles) {
                if (x > tile.getSpotX() && x < tile.getSpotX() + 40 && y > tile.getSpotY()
                        && y < tile.getSpotY() + 40) {
                    return tile;
                }
            }
        }
        return null;
    }
}
