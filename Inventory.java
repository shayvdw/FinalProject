import javax.swing.*;
import java.awt.Graphics;

public class Inventory extends JPanel {
    int width = 500;
    int height = 300;
    JFrame frame = new JFrame();

    public Inventory(){
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.add(this);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setVisible(false);

        int[][] Inventory = new int[4][8];
    }

    public void hide(){
        frame.setVisible(false);
    }

    public void show(){
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                g.fillRect(10 + i * 50, 10 + j * 50, 40, 40);
            }
        }
    }
}
