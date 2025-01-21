/*
 * is the main game loop
 * uses thread so mutiple parts of game can run at once
 */
import java.awt.*;
import java.util.Scanner;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable {
    int width = 500;
    int height = 500;

    JFrame frame = new JFrame();

    Keyboard k = new Keyboard();
    Mouse mouse = new Mouse();

    Inventory i;

    InventorySquare[][] inventory = new InventorySquare[8][4];

    Player player;

    int playerSpeed = 5;

    public Screen() {
        //reads in save file to make inventory
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

        i = new Inventory(inventory);

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
