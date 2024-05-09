import java.awt.*;

public class Pipe {
    int x,y;
    int width,height;
    Image img;
    Boolean passed = false;

    Pipe(int x,int y,int width,int height,Image img){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
    }
}
