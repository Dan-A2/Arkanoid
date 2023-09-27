import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Prize extends Rectangle {

    private static BufferedImage fireBall;
    private static BufferedImage bigPaddle;
    private static BufferedImage dazyPaddle;
    private static BufferedImage fastBall;
    private static BufferedImage multipleBalls;
    private static BufferedImage slowBall;
    private static BufferedImage smallPaddle;
    private static BufferedImage random;
    private PRIZE p;
    long lastUsed = -10;
    long timeTaken = 0;
    private BufferedImage image;

    public Prize(int w, int h, int x, int y, PRIZE kooft, BufferedImage image){
        super(x, y, w, h);
        this.p = kooft;
        this.image = image;
    }

    enum PRIZE {
        FireBall,
        MultipleBalls,
        FastBall,
        SlowBall,
        BiggerPaddle,
        SmallerPaddle,
        DazyPaddle,
        Random
    }

    public PRIZE getPrize() {
        return p;
    }

    public void setY(int ballY) {
        this.y = ballY;
    }

    public void usePrize(){
        boolean isRepeated = false;
        for (Prize prize : GamePanel.prizesInUse) {
            if (prize.getPrize() == this.getPrize() && prize.getPrize() != PRIZE.MultipleBalls) {
                System.out.println("prize " + this.getPrize() + " is repeated");
                isRepeated = true;
                break;
            }
        }
        if(!isRepeated) {
            switch (this.getPrize()) {
                case FireBall:
                    for (Ball ball : GamePanel.balls) {
                        ball.setOnFire(true);
                    }
                    GamePanel.prizesInUse.add(this);
                    System.out.println("ball is on fire");
                    if(this.lastUsed == -10) {
                        this.lastUsed = System.currentTimeMillis();
                    }
                    break;
                case FastBall:
                    for (Ball ball : GamePanel.balls) {
                        ball.setXVelocity(ball.getXVelocity() * 2);
                        ball.setYVelocity(ball.getYVelocity() * 2);
                    }
                    System.out.println("ball moves faster");
                    GamePanel.prizesInUse.add(this);
                    if(this.lastUsed == -10) {
                        this.lastUsed = System.currentTimeMillis();
                    }
                    break;
                case SlowBall:
                    for (Ball ball : GamePanel.balls) {
                        ball.setXVelocity(ball.getXVelocity() / 2);
                        ball.setYVelocity(ball.getYVelocity() / 2);
                    }
                    System.out.println("ball moves slower");
                    GamePanel.prizesInUse.add(this);
                    if(this.lastUsed == -10) {
                        this.lastUsed = System.currentTimeMillis();
                    }
                    break;
                case MultipleBalls:
                    GamePanel.createNewBall();
                    System.out.println("another ball");
                    GamePanel.prizesInUse.add(this);
                    if(this.lastUsed == -10) {
                        this.lastUsed = System.currentTimeMillis();
                    }
                    break;
                case BiggerPaddle:
                    GamePanel.paddle.setBounds(GamePanel.paddle.x, GamePanel.paddle.y, GamePanel.paddle.width * 2, GamePanel.paddle.height);
                    System.out.println("bigger paddle");
                    GamePanel.prizesInUse.add(this);
                    if(this.lastUsed == -10) {
                        this.lastUsed = System.currentTimeMillis();
                    }
                    break;
                case SmallerPaddle:
                    GamePanel.paddle.setBounds(GamePanel.paddle.x, GamePanel.paddle.y, GamePanel.paddle.width / 2, GamePanel.paddle.height);
                    System.out.println("smaller paddle");
                    GamePanel.prizesInUse.add(this);
                    if(this.lastUsed == -10) {
                        this.lastUsed = System.currentTimeMillis();
                    }
                    break;
                case DazyPaddle:
                    GamePanel.isPaddleReversed = true;
                    System.out.println("dazy paddle");
                    GamePanel.prizesInUse.add(this);
                    if(this.lastUsed == -10) {
                        this.lastUsed = System.currentTimeMillis();
                    }
                    break;
                case Random:
                    Random random = new Random();
                    int x = random.nextInt(6) + 1;
                    if (x == 1) {
                        isRepeated = false;
                        for (Prize prize : GamePanel.prizesInUse) {
                            if (prize.getPrize() == PRIZE.FireBall) {
                                isRepeated = true;
                                System.out.println("random fire repeated");
                                break;
                            }
                        }
                        if(!isRepeated) {
                            for (Ball ball : GamePanel.balls) {
                                ball.setOnFire(true);
                            }
                            System.out.println("random fire");
                            Prize prize = new Prize(0, 0, 0, 0, PRIZE.FireBall, fireBall);
                            GamePanel.prizesInUse.add(prize);
                            prize.lastUsed = System.currentTimeMillis();
                        }
                    } else if (x == 2) {
                        isRepeated = false;
                        for (Prize prize : GamePanel.prizesInUse) {
                            if (prize.getPrize() == PRIZE.FastBall) {
                                isRepeated = true;
                                System.out.println("random fastball repeated");
                                break;
                            }
                        }
                        if(!isRepeated) {
                            for (Ball ball : GamePanel.balls) {
                                ball.setXVelocity(ball.getXVelocity() * 2);
                                ball.setYVelocity(ball.getYVelocity() * 2);
                            }
                            Prize prize = new Prize(0, 0, 0, 0, PRIZE.FastBall, fastBall);
                            System.out.println("random fast ball");
                            GamePanel.prizesInUse.add(prize);
                            prize.lastUsed = System.currentTimeMillis();
                        }
                    } else if (x == 3) {
                        isRepeated = false;
                        for (Prize prize : GamePanel.prizesInUse) {
                            if (prize.getPrize() == PRIZE.SlowBall) {
                                System.out.println("random slow ball repeated");
                                isRepeated = true;
                                break;
                            }
                        }
                        if(!isRepeated) {
                            for (Ball ball : GamePanel.balls) {
                                ball.setXVelocity(ball.getXVelocity() / 2);
                                ball.setYVelocity(ball.getYVelocity() / 2);
                            }
                            Prize prize = new Prize(0, 0, 0, 0, PRIZE.SlowBall, slowBall);
                            System.out.println("random slow ball");
                            GamePanel.prizesInUse.add(prize);
                            prize.lastUsed = System.currentTimeMillis();
                        }
                    } else if (x == 4) {
                        GamePanel.createNewBall();
                        System.out.println("random another ball");
                    } else if (x == 5) {
                        isRepeated = false;
                        for (Prize prize : GamePanel.prizesInUse) {
                            if (prize.getPrize() == PRIZE.BiggerPaddle) {
                                isRepeated = true;
                                System.out.println("random bigger paddle repeated");
                                break;
                            }
                        }
                        if(!isRepeated) {
                            GamePanel.paddle.setBounds(GamePanel.paddle.x, GamePanel.paddle.y, GamePanel.paddle.width * 2, GamePanel.paddle.height);
                            Prize prize = new Prize(0, 0, 0, 0, PRIZE.BiggerPaddle, bigPaddle);
                            System.out.println("random biggpadle");
                            GamePanel.prizesInUse.add(prize);
                            prize.lastUsed = System.currentTimeMillis();
                        }
                    } else if (x == 6) {
                        isRepeated = false;
                        for (Prize prize : GamePanel.prizesInUse) {
                            if (prize.getPrize() == PRIZE.SmallerPaddle) {
                                System.out.println("ramdom small paddle repeated");
                                isRepeated = true;
                                break;
                            }
                        }
                        if(!isRepeated) {
                            Prize prize = new Prize(0, 0, 0, 0, PRIZE.SmallerPaddle, smallPaddle);
                            GamePanel.prizesInUse.add(prize);
                            System.out.println("random small paddle");
                            prize.lastUsed = System.currentTimeMillis();
                            GamePanel.paddle.setBounds(GamePanel.paddle.x, GamePanel.paddle.y, GamePanel.paddle.width / 2, GamePanel.paddle.height);
                        }
                    } else {
                        isRepeated = false;
                        for (Prize prize : GamePanel.prizesInUse) {
                            if (prize.getPrize() == PRIZE.DazyPaddle) {
                                isRepeated = true;
                                System.out.println("random dazypaddle repeated");
                                break;
                            }
                        }
                        if(!isRepeated) {
                            GamePanel.isPaddleReversed = true;
                            Prize prize = new Prize(0, 0, 0, 0, PRIZE.DazyPaddle, dazyPaddle);
                            System.out.println("random dazy paddle");
                            GamePanel.prizesInUse.add(prize);
                            prize.lastUsed = System.currentTimeMillis();
                        }
                    }
            }
        }
    }

    public void inactivatePrize(){

        switch (this.getPrize()) {
            case FireBall:
                for (Ball ball : GamePanel.balls) {
                    ball.setOnFire(false);
                }
                break;
            case FastBall:
                for (Ball ball : GamePanel.balls) {
                    ball.setXVelocity(ball.getXVelocity() / 2);
                    ball.setYVelocity(ball.getYVelocity() / 2);
                }
                break;
            case SlowBall:
                for (Ball ball : GamePanel.balls) {
                    ball.setXVelocity(ball.getXVelocity() * 2);
                    ball.setYVelocity(ball.getYVelocity() * 2);
                }
                break;
            case BiggerPaddle:
                GamePanel.paddle.setBounds(GamePanel.paddle.x, GamePanel.paddle.y, GamePanel.paddle.width / 2, GamePanel.paddle.height);
                break;
            case SmallerPaddle:
                GamePanel.paddle.setBounds(GamePanel.paddle.x, GamePanel.paddle.y, GamePanel.paddle.width * 2, GamePanel.paddle.height);
                break;
            case DazyPaddle:
                GamePanel.isPaddleReversed = false;
                break;
        }

    }

    public static BufferedImage getFireBall() {
        return fireBall;
    }

    public static void setFireBall(BufferedImage fiReBall) {
        fireBall = fiReBall;
    }

    public static BufferedImage getBigPaddle() {
        return bigPaddle;
    }

    public static void setBigPaddle(BufferedImage bIgPaddle) {
        bigPaddle = bIgPaddle;
    }

    public static BufferedImage getDazyPaddle() {
        return dazyPaddle;
    }

    public static void setDazyPaddle(BufferedImage daZyPaddle) {
        dazyPaddle = daZyPaddle;
    }

    public static BufferedImage getFastBall() {
        return fastBall;
    }

    public static void setFastBall(BufferedImage faStPaddle) {
        fastBall = faStPaddle;
    }

    public static BufferedImage getMultipleBalls() {
        return multipleBalls;
    }

    public static void setMultipleBalls(BufferedImage mulTipleBalls) {
        multipleBalls = mulTipleBalls;
    }

    public static BufferedImage getSlowBall() {
        return slowBall;
    }

    public static void setSlowBall(BufferedImage slOwPaddle) {
        slowBall = slOwPaddle;
    }

    public static BufferedImage getSmallPaddle() {
        return smallPaddle;
    }

    public static void setSmallPaddle(BufferedImage smAllPaddle) {
        smallPaddle = smAllPaddle;
    }

    public static BufferedImage getRandom() {
        return random;
    }

    public static void setRandom(BufferedImage raNdom) {
        random = raNdom;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}