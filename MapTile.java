import java.awt.Color;
import java.awt.Graphics;

public class MapTile extends Square {

    Item item;

    public MapTile(boolean hasItem, int spotY, int spotX, int gridX, int gridY, int itemWidth, int itemHeight,
            int itemID) {
        super(spotY, spotY, gridX, gridY);
        if (hasItem) {
            this.item = new Item(this, itemWidth, itemHeight, itemID);
        }
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void drawSquare(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(gridX * 40, gridY * 40, 39, 39);
    }
}
