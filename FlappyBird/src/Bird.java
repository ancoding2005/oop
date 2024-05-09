import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

class Bird extends Image {
    int x;
    int y;
    Image img;

    Bird(int x,int y,Image img) {
        this.x = x;
        this.y = y;
        this.img = img;
    }

    @Override
    public int getWidth(ImageObserver observer) {
        return 0;
    }

    @Override
    public int getHeight(ImageObserver observer) {
        return 0;
    }

    @Override
    public ImageProducer getSource() {
        return null;
    }

    @Override
    public Graphics getGraphics() {
        return null;
    }

    @Override
    public Object getProperty(String name, ImageObserver observer) {
        return null;
    }
}