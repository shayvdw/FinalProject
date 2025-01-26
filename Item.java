import java.awt.Color;
import java.awt.Graphics;

/**
 * Draws the item.
 * The color of the item depends on its itemID.
 * The item is drawn on the inventory square or at the mouse position depending on whether it is held.
 *
 * @param g the Graphics object used for drawing
 * @param MouseX the x-coordinate of the mouse, used if the item is held
 * @param MouseY the y-coordinate of the mouse, used if the item is held
 */
public class Item {
    Square square;

    private int itemX;
    private int itemY;

    private int itemWidth;
    private int itemHeight;
    private int itemID;

    private boolean isGrabbed;
    private boolean isHeld;

    public Item(Square square, int itemWidth, int itemHeight, int itemID) {
        this.square = square;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        this.itemID = itemID;
        this.itemX = square.getGridX();
        this.itemY = square.getGridY();
        if(square != null){
            this.isHeld = true;
        }
    }
    //set the square of the item
    public void setSquare(Square square) {
        this.square = square;
    }
    /**
     * draws the item
     * Color depends on itemID
     * the item is drawn on the inventory square or mouse depending on if its held
     * @param MouseX the x-coordinate of the mouse, used if the item is held
     * @param MouseY the y-coordinate of the mouse, used if the item is held
     * @param g the graphic object used
     */
    public void drawItem(Graphics g, int MouseX, int MouseY) {
        if (itemID == 1) {
            g.setColor(Color.RED);
        } else if (itemID == 2) {
            g.setColor(Color.GREEN);
        } else if (itemID == 3) {
            g.setColor(Color.BLUE);
        }else{
            g.setColor(Color.WHITE);
        }
        if (itemID != 0) {
            if (isGrabbed) {
                g.fillRect(MouseX - itemWidth / 2, MouseY - itemHeight / 2, itemWidth, itemHeight);
            } else {
                g.fillRect(square.getSpotX() + (40 - itemWidth)/2, square.getSpotY() + (40 - itemHeight)/2, itemWidth, itemHeight);
            }
        }
    }
    //returns if the item is in the inventory
    public boolean isHeld(){
        return isHeld;
    }
    //is used to tell if item is in inventory
    public void hold(){
        isHeld = !isHeld;
    }
    //for snapping items to the inventory
    public void updateItem() {
        itemX = square.getGridX() + (40 - itemWidth) / 2;
        itemY = square.getGridY() + (40 - itemHeight) / 2;
    }
    //returns the x position of the item
    public int getItemX() {
        return itemX;
    }
    //returns the y position of the item
    public int getItemY() {
        return itemY;
    }
    //toggles the isGrabbed boolean
    public void clicked() {
        isGrabbed = !isGrabbed;
    }
    //return whether the item is grabbed or not
    public boolean isGrabbed() {
        return isGrabbed;
    }
    //returns the itemID
    public int getItemID() {
        return itemID;
    }
    //returns the width of the item
    public int getItemWidth() {
        return itemWidth;
    }
    //returns the height of the item
    public int getItemHeight() {
        return itemHeight;
    } 

}