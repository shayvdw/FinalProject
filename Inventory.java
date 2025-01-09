import javax.swing.*;
import java.awt.Graphics;

public class Inventory extends JPanel {
    int width = 500;
    int height = 300;
    JFrame frame = new JFrame();
    int[][] inventory;
    boolean show = false;

    public Inventory(){
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.add(this);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setVisible(false);

        inventory = new int[4][8];
    }

    public void hide(){
        frame.setVisible(!show);
    }

    public void show(){
        frame.setVisible(show);
    }

    public boolean isShown(){
        return show;
    }

    public void paintComponent(Graphics g) {
        for (int i = 0; i < inventory[0].length; i++) {
            for (int j = 0; j < inventory.length; j++) {
                g.fillRect(10 + i * 50, 10 + j * 50, 40, 40);
            }
        }
    }
}
