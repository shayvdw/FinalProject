import javax.swing.JComponent;

public class InventorySquare extends JComponent {
    int spotX;
    int spotY;
    Item sprite;
    boolean grabbed;

    public InventorySquare(int x, int y) {
        this.spotX = x;
        this.spotY = y;
        this.grabbed = false;
        this.sprite = new Item(this, 30, 30, (int) (Math.random() * 3) + 1);

    }

    public void setSpotX(int spotX) {
        this.spotX = spotX;
    }

    public void setSpotY(int spotY) {
        this.spotY = spotY;
    }

    public int getSpotX() {
        return spotX;
    }

    public int getSpotY() {
        return spotY;
    }

    // public void clicked() {
    //     grabbed = !grabbed;
    // }

    public boolean isGrabbed() {
        return grabbed;
    }

    public void setItem(Item item) {
        this.sprite = item;
    }

    public Item getSprite() {
        return sprite;
    }
}
