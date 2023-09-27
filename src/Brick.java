import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Brick extends Rectangle {

    static int h = 10;
    static int w = 30;
    private static final Random random = new Random();
    private int shot;
    private TYPE type;
    private Prize prize;
    private final static LinkedList<Brick> bricks = new LinkedList<>();
    private boolean visible;
    private static long lastChanged;
    private static long lastRowAdd;

    public Brick(int w, int h, int x, int y){
        super(x, y, w, h);
    }

    public static void newBricks(){
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 6; j++) {
                int type = random.nextInt(5) + 1;
                Brick brick = new Brick(w, h, (50*i)+10, (40*j)+40);
                brick.shot = 0;
                if(type == 1){
                    brick.type = TYPE.Glassy;
                }
                else if(type == 2){
                    brick.type = TYPE.Invisible;
                }
                else if(type == 3){
                    brick.type = TYPE.Wooden;
                }
                else if(type == 4){
                    brick.type = TYPE.Blinking;
                }
                else{
                    brick.type = TYPE.ContainPrize;
                    int prize = random.nextInt(8)+1;
                    if(prize == 1){
                        brick.prize = new Prize(10, 10, (int) brick.getX(), (int) brick.getY(), Prize.PRIZE.FireBall, Prize.getFireBall());
                    }
                    else if(prize == 2){
                        brick.prize = new Prize(10, 10, (int) brick.getX(), (int) brick.getY(), Prize.PRIZE.MultipleBalls, Prize.getMultipleBalls());
                    }
                    else if(prize == 3){
                        brick.prize = new Prize(10, 10, (int) brick.getX(), (int) brick.getY(), Prize.PRIZE.FastBall, Prize.getFastBall());
                    }
                    else if(prize == 4){
                        brick.prize = new Prize(10, 10, (int) brick.getX(), (int) brick.getY(), Prize.PRIZE.SlowBall, Prize.getSlowBall());
                    }
                    else if(prize == 5){
                        brick.prize = new Prize(10, 10, (int) brick.getX(), (int) brick.getY(), Prize.PRIZE.BiggerPaddle, Prize.getBigPaddle());
                    }
                    else if(prize == 6){
                        brick.prize = new Prize(10, 10, (int) brick.getX(), (int) brick.getY(), Prize.PRIZE.SmallerPaddle, Prize.getSlowBall());
                    }
                    else if(prize == 7){
                        brick.prize = new Prize(10, 10, (int) brick.getX(), (int) brick.getY(), Prize.PRIZE.DazyPaddle, Prize.getDazyPaddle());
                    }
                    else{
                        brick.prize = new Prize(10, 10, (int) brick.getX(), (int) brick.getY(), Prize.PRIZE.Random, Prize.getRandom());
                    }
                }
                brick.visible = true;
                bricks.add(brick);
            }
        }
        lastChanged = System.currentTimeMillis();
        lastRowAdd = lastChanged;
    }

    public static void addRow(){

        for (Brick brick : bricks) {
            brick.y += 40;
            if(brick.getPrize() != null){
                brick.getPrize().y += 40;
            }
        }
        for (int i = 0; i < 15; i++) {
            int type = random.nextInt(5) + 1;
            Brick brick = new Brick(w, h, (50*i)+10, 40);
            brick.shot = 0;
            if(type == 1){
                brick.type = TYPE.Glassy;
            }
            else if(type == 2){
                brick.type = TYPE.Invisible;
            }
            else if(type == 3){
                brick.type = TYPE.Wooden;
            }
            else if(type == 4){
                brick.type = TYPE.Blinking;
            }
            else{
                brick.type = TYPE.ContainPrize;
                int prize = random.nextInt(8)+1;
                if(prize == 1){
                    brick.prize = new Prize(10, 10, (int) brick.getX(), (int) brick.getY(), Prize.PRIZE.FireBall, Prize.getFireBall());
                }
                else if(prize == 2){
                    brick.prize = new Prize(10, 10, (int) brick.getX(), (int) brick.getY(), Prize.PRIZE.MultipleBalls, Prize.getMultipleBalls());
                }
                else if(prize == 3){
                    brick.prize = new Prize(10, 10, (int) brick.getX(), (int) brick.getY(), Prize.PRIZE.FastBall, Prize.getFastBall());
                }
                else if(prize == 4){
                    brick.prize = new Prize(10, 10, (int) brick.getX(), (int) brick.getY(), Prize.PRIZE.SlowBall, Prize.getSlowBall());
                }
                else if(prize == 5){
                    brick.prize = new Prize(10, 10, (int) brick.getX(), (int) brick.getY(), Prize.PRIZE.BiggerPaddle, Prize.getBigPaddle());
                }
                else if(prize == 6){
                    brick.prize = new Prize(10, 10, (int) brick.getX(), (int) brick.getY(), Prize.PRIZE.SmallerPaddle, Prize.getSlowBall());
                }
                else if(prize == 7){
                    brick.prize = new Prize(10, 10, (int) brick.getX(), (int) brick.getY(), Prize.PRIZE.DazyPaddle, Prize.getDazyPaddle());
                }
                else{
                    brick.prize = new Prize(10, 10, (int) brick.getX(), (int) brick.getY(), Prize.PRIZE.Random, Prize.getRandom());
                }
            }
            brick.visible = true;
            bricks.add(brick);
            lastRowAdd = System.currentTimeMillis();
        }

    }

    public static LinkedList<Brick> getBricks() {
        return bricks;
    }

    enum TYPE {
        Glassy,
        Wooden,
        Invisible,
        Blinking,
        ContainPrize
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    public TYPE getType() {
        return type;
    }

    public int getShot() {
        return shot;
    }

    public void collapse(){
        this.shot++;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public static long getLastChanged() {
        return lastChanged;
    }

    public static void setLastChanged(long lastChanged) {
        Brick.lastChanged = lastChanged;
    }

    public Prize getPrize() {
        return prize;
    }

    public static int getH() {
        return h;
    }

    public static int getW() {
        return w;
    }

    public void setShot(int shot) {
        this.shot = shot;
    }

    public static long getLastRowAdd() {
        return lastRowAdd;
    }

    public static void setLastRowAdd(long lastRowAdd) {
        Brick.lastRowAdd = lastRowAdd;
    }
}