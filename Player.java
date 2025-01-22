import java.awt.Color;
import java.awt.Graphics;

public class Player {
    private int x;
    private int y;
    
//constructor for the player
public Player(int x, int y){
    this.x = x;
    this.y = y;
}

    //returns the x position of the player
    public int getX() {
        return x;
    }
    //returns the y position of the player
    public int getY() {
        return y;
    }
    //moves the player
    public void move(int x, int y){
        this.x += x;
        this.y += y;
    }
    //draws the player
    public void drawPlayer(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(x-10, y-10, 20, 20);
    }
}
