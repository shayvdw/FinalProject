/*
 * is used to create snap points for items in inventory
 * feature to add:
 * - number of items in spot
 * 
 */
import javax.swing.JComponent;

public class InventorySquare extends JComponent {
    private int spotX;
    private int spotY;
    private Item item;

    public InventorySquare(int x, int y, int itemID,int width,int height) {
        this.spotX = x;
        this.spotY = y;
        this.item = new Item(this, width, height, itemID);

    }
    //returns the x position of the square
    public int getSpotX() {
        return spotX;
    }
    //returns the y position of the square
    public int getSpotY() {
        return spotY;
    }
    //sets the item in the square
    public void setItem(Item item) {
        this.item = item;
    }
    //returns the item in the square
    public Item getItem() {
        return item;
    }

}
