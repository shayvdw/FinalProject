/*
 * is used to create snap points for items in inventory
 * feature to add:
 * - number of items in spot
 * 
 */


public class InventorySquare extends Square {
    private int spotX;
    private int spotY;
    private Item item;

    public InventorySquare(int spotX, int SpotY, int gridX, int gridY, int itemID,int width,int height) {
        super(spotX, SpotY, gridX, gridY);
        this.item = new Item(this, width, height, itemID);

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
