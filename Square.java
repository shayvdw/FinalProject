
import javax.swing.JComponent;
public class Square extends JComponent{
    int spotX;
    int spotY;
    int gridX;
    int gridY;

    public Square(int spotX, int spotY, int gridX, int gridY){
        this.spotX = spotX;
        this.spotY = spotY;
        this.gridX = gridX;
        this.gridY = gridY;
    }

    public int getSpotX(){
        return spotX;
    }

    public int getSpotY(){
        return spotY;
    }

    public int getGridX(){
        return gridX;
    }

    public int getGridY(){
        return gridY;
    }
}
