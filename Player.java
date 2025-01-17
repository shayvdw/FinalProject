import java.awt.Color;
import java.awt.Graphics;

public class Player {
    private int x;
    private int y;

public Player(int x, int y){
    this.x = x;
    this.y = y;
}


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int x, int y){
        this.x += x;
        this.y += y;
    }

    public void drawPlayer(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 20, 20);
    }
}
