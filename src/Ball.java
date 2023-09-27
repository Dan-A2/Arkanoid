import java.awt.*;

public class Ball extends Rectangle {

    private boolean onFire;
    private double xVelocity;
    private double yVelocity;
    private final static double BALL_X_V = 3.0;
    private final static double BALL_Y_V = 4.0;
    private long lastChanged;

    public Ball(int x, int y, int w, int h){
        super(x, y, w, h);
        xVelocity = BALL_X_V;
        yVelocity = BALL_Y_V;
        this.onFire = false;
    }

    public static double getBallXV() {
        return BALL_X_V;
    }

    public static double getBallYV() {
        return BALL_Y_V;
    }

    public void setBallX(int ballX) {
        this.x = ballX;
    }

    public void setBallY(int ballY) {
        this.y = ballY;
    }

    public boolean isOnFire() {
        return onFire;
    }

    public void setOnFire(boolean onFire) {
        this.onFire = onFire;
    }

    public double getXVelocity() {
        return xVelocity;
    }

    public void setXVelocity(double ballXVelocity) {
        xVelocity = ballXVelocity;
    }

    public double getYVelocity() {
        return yVelocity;
    }

    public void setYVelocity(double ballYVelocity) {
        yVelocity = ballYVelocity;
    }

    public long getLastChanged() {
        return lastChanged;
    }

    public void setLastChanged(long lastChanged) {
        this.lastChanged = lastChanged;
    }
}