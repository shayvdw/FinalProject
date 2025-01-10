import javax.swing.JComponent;

public class InventorySquare extends JComponent {
    int spotX;
    int spotY;

    public InventorySquare(int x, int y) {
        this.spotX = x;
        this.spotY = y;
    }

    public int getSpotX() {
        return spotX;
    }

    public int getSpotY() {
        return spotY;
    }
}
