import java.awt.*;

public class Paddle extends Rectangle {

    public Paddle(int h, int w, int x, int y){
        super(x, y, w ,h);
    }

    public void setPaddleX(int paddleX) {
        this.x = paddleX;
    }

}