import java.awt.Color;
import java.awt.Graphics;

public class Item {
    InventorySquare square;

    int itemX;
    int itemY;

    int itemWidth;
    int itemHeight;
    int itemID;

    boolean isGrabbed;
    boolean isHeld;

    public Item(InventorySquare square, int itemWidth, int itemHeight, int itemID) {
        this.square = square;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        this.itemID = itemID;
        this.itemX = square.getSpotX() + (40 - itemWidth) / 2;
        this.itemY = square.getSpotY() + (40 - itemHeight) / 2;
        if(square != null){
            this.isHeld = true;
        }else{
            this.isHeld = false;
        }
    }

    public void setSquare(InventorySquare square) {
        this.square = square;
    }

    public void drawItem(Graphics g, int MouseX, int MouseY) {
        if (itemID == 1) {
            g.setColor(Color.RED);
        } else if (itemID == 2) {
            g.setColor(Color.GREEN);
        } else if (itemID == 3) {
            g.setColor(Color.BLUE);
        }
        if (itemID != 0) {
            if (isGrabbed) {
                g.fillRect(MouseX - itemWidth / 2, MouseY - itemHeight / 2, itemWidth, itemHeight);
            } else {
                g.fillRect(itemX, itemY, itemWidth, itemHeight);
            }
        }
    }

    public boolean isHeld(){
        return isHeld;
    }

    public void setItemX(int itemX) {
        this.itemX = itemX;
    }

    public void setItemY(int itemY) {
        this.itemY = itemY;
    }

    public void updateItem() {
        itemX = square.getSpotX() + (40 - itemWidth) / 2;
        itemY = square.getSpotY() + (40 - itemHeight) / 2;
    }

    public int getItemX() {
        return itemX;
    }

    public int getItemY() {
        return itemY;
    }

    public void clicked() {
        isGrabbed = !isGrabbed;
    }

    public boolean isGrabbed() {
        return isGrabbed;
    }

    public int getItemID() {
        return itemID;
    }

}