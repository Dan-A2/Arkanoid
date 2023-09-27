import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class GamePanel extends JPanel implements ActionListener {

    private static final int SCREEN_WIDTH = 750;
    private static final int SCREEN_HEIGHT = 750;
    private static final int DELAY = 10;
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 15;
    private static final int BRICK_WIDTH = 30;
    private static final int BRICK_HEIGHT = 10;
    private static final int PRIZE_VELOCITY = 3;
    private static final int BALL_RADIOS = 10;
    private static int PADDLE_VELOCITY = 10;
    static int lives = 3, score = 0;
    static LinkedList<Ball> balls = new LinkedList<>();
    static LinkedList<Prize> prizesToBeShown = new LinkedList<>();
    static LinkedList<Prize> prizesInUse = new LinkedList<>();
    boolean running;
    boolean started;
    boolean pauzed;
    Timer timer;
    static Paddle paddle;
    static boolean isPaddleReversed = false;
    User currentUser;

    GamePanel(User user) throws IOException {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(new MyKeyAdapter());
        Prize.setFireBall(ImageIO.read(new File("fireBallGift.png")));
        Prize.setBigPaddle(ImageIO.read(new File("theBigPaddleGift.png")));
        Prize.setDazyPaddle(ImageIO.read(new File("theConfusedPaddle.png")));
        Prize.setFastBall(ImageIO.read(new File("theFastPaddleGift.png")));
        Prize.setMultipleBalls(ImageIO.read(new File("theMultipleBalls.png")));
        Prize.setSlowBall(ImageIO.read(new File("theSlowPaddleGift.png")));
        Prize.setSmallPaddle(ImageIO.read(new File("theSmallPaddleGift.png")));
        Prize.setRandom(ImageIO.read(new File("RandomGift.png")));
        currentUser = user;
        startGame();
    }

    public void startGame(){
        newComponents();
//        running = true;
        pauzed = false;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void newComponents(){
        createNewBall();
        paddle = new Paddle(PADDLE_HEIGHT, PADDLE_WIDTH, 345, 715);
        Brick.newBricks();
    }

    public static void createNewBall(){
        Ball ball = new Ball(365, 695, BALL_RADIOS, BALL_RADIOS);
        System.out.println(ball.getXVelocity() + " " + ball.getYVelocity());
        ball.setYVelocity(-Ball.getBallYV());
        balls.add(ball);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if(running && started) {
            // ScoreBoard
            g.setColor(Color.orange);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + score + "   Lives: " + lives, (SCREEN_WIDTH - metrics.stringWidth("Score: " + score + "   Lives: " + lives)) / 2, g.getFont().getSize());
            depict(g);
        }
        else if(!started && !running){
            // score board
            g.setColor(Color.orange);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Press ENTER to start", (SCREEN_WIDTH - metrics.stringWidth("Press ENTER to start")) / 2, g.getFont().getSize());
            depict(g);
        }
        else if(started && !running){ // gameover
            g.setColor(Color.orange);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Gameover     score: " + score, (SCREEN_WIDTH - metrics.stringWidth("Gameover     score: " + score)) / 2, g.getFont().getSize());
            g.drawString("Press ENTER to restart", (SCREEN_WIDTH - metrics.stringWidth("Press ENTER to restart")) / 2, SCREEN_HEIGHT/2);
        }
    }

    public void depict(Graphics g){
        // bemola Bricks
        boolean flag = false;
        for (int i = 0; i < Brick.getBricks().size(); i++) {
            Brick brick = Brick.getBricks().get(i);
            if(System.currentTimeMillis() - Brick.getLastChanged() > 2000){
                flag = true;
                if(brick.getType() == Brick.TYPE.Blinking){
                    brick.setVisible(!brick.isVisible());
                }
            }
        }
        if(flag){
            Brick.setLastChanged(System.currentTimeMillis());
        }
        for (int i = 0; i < Brick.getBricks().size(); i++) {
            switch (Brick.getBricks().get(i).getType()){
                case Glassy:
                    g.setColor(new Color(33, 107, 234));
                    break;
                case Wooden:
                    if(Brick.getBricks().get(i).getShot() == 0) {
                        g.setColor(new Color(141, 61, 35, 156));
                    }
                    else{
                        g.setColor(new Color(174, 75, 43, 255));
                    }
                    break;
                case Blinking:
                    g.setColor(new Color(250, 255, 0));
                    break;
                case Invisible:
                    g.setColor(Color.lightGray);
                    break;
                case ContainPrize:
                    g.setColor(Color.magenta);
                    break;
            }
            if(Brick.getBricks().get(i).isVisible()) {
                g.fillRect((int) Brick.getBricks().get(i).getX(), (int) Brick.getBricks().get(i).getY(), BRICK_WIDTH, BRICK_HEIGHT);
            }
        }
        // prizes
        for (int i = 0; i < prizesToBeShown.size(); i++) {
            Prize tmp = prizesToBeShown.get(i);
            switch (tmp.getPrize()){
                case FireBall:
                    g.drawImage(Prize.getFireBall(), (int) tmp.getX(), (int) tmp.getY(), this);
                    break;
                case FastBall:
                    g.drawImage(Prize.getFastBall(), (int) tmp.getX(), (int) tmp.getY(), this);
                    break;
                case SlowBall:
                    g.drawImage(Prize.getSlowBall(), (int) tmp.getX(), (int) tmp.getY(), this);
                    break;
                case MultipleBalls:
                    g.drawImage(Prize.getMultipleBalls(), (int) tmp.getX(), (int) tmp.getY(), this);
                    break;
                case BiggerPaddle:
                    g.drawImage(Prize.getBigPaddle(), (int) tmp.getX(), (int) tmp.getY(), this);
                    break;
                case SmallerPaddle:
                    g.drawImage(Prize.getSmallPaddle(), (int) tmp.getX(), (int) tmp.getY(), this);
                    break;
                case DazyPaddle:
                    g.drawImage(Prize.getDazyPaddle(), (int) tmp.getX(), (int) tmp.getY(), this);
                    break;
                case Random:
                    g.drawImage(Prize.getRandom(), (int) tmp.getX(), (int) tmp.getY(), this);
                    break;
            }
        }
        // paddle
        g.setColor(Color.green);
        g.fillRect((int) paddle.getX(), (int) paddle.getY(), paddle.width, paddle.height);
        // balls
        for (Ball ball : balls) {
            if(ball.isOnFire()){
                g.setColor(Color.orange);
            }
            else {
                g.setColor(Color.CYAN);
            }
            g.fillOval((int) ball.getX(), (int) ball.getY(), BALL_RADIOS, BALL_RADIOS);
        }
    }

    public void move(){
        for (Ball ball : balls) {
            ball.setBallX((int) (ball.getX() + ball.getXVelocity()));
            ball.setBallY((int) (ball.getY() + ball.getYVelocity()));
        }
        for (Prize prize : prizesToBeShown) {
            prize.setY((int) prize.getY() + PRIZE_VELOCITY);
        }
    }

    public void checkRow(){

        if(System.currentTimeMillis() - Brick.getLastRowAdd() >= 10000){
            Brick.addRow();
            Brick.setLastRowAdd(System.currentTimeMillis());
        }

    }

    public void checkCollisions(){
        for (int i = 0; i<balls.size(); i++) {
            Ball ball = balls.get(i);
            // Right & Left border
            if(ball.getX() >= 750 || ball.getX() <= 0){
                ball.setXVelocity(-ball.getXVelocity());
            }
            // Top border
            if(ball.getY() <= 0){
                ball.setYVelocity(-ball.getYVelocity());
            }
            // Bottom border
            if(ball.getY() >= 750){
                if(balls.size() > 1){
                    balls.remove(ball);
                    score--;
                    continue;
                }
                else if(lives > 0){
                    lives --;
                    score -= 10;
                    ball.setYVelocity(-ball.getYVelocity());
                }
                else{
                    running = false;
                    gameOver(currentUser);
                }
            }
            // Paddle
            if(ball.intersects(paddle)){
                System.out.println(ball.getXVelocity() + " " + ball.getYVelocity());
                double halfPaddle = paddle.width/2;
                double x;
                if(ball.getCenterX() <= paddle.getCenterX()) {
                    x = Math.abs(ball.getCenterX() - paddle.getX());
                } else {
                    x = Math.abs(paddle.getX() + PADDLE_WIDTH - ball.getCenterX());
                }
                ball.setYVelocity(ball.getYVelocity() * (1.0 + 0.0005 * ((halfPaddle-x)/halfPaddle)));
                if(ball.getXVelocity() > 0) {
                    ball.setXVelocity(Math.sqrt(25 - ball.getYVelocity() * ball.getYVelocity()));
                } else {
                    ball.setXVelocity(-Math.sqrt(25 - ball.getYVelocity() * ball.getYVelocity()));
                }
                ball.setYVelocity(-ball.getYVelocity());
                /* getting sure of everything! */
                if(ball.getXVelocity() > 10){
                    ball.setXVelocity(Ball.getBallXV());
                }
                else if(ball.getXVelocity() < -10){
                    ball.setXVelocity(-Ball.getBallXV());
                }
                else if(ball.getYVelocity() > 10){
                    ball.setYVelocity(Ball.getBallYV());
                }
                else if(ball.getYVelocity() < -10){
                    ball.setYVelocity(-Ball.getBallYV());
                }
                /**********************************************/
//                System.out.println(ball.getXVelocity() + " " + ball.getYVelocity());
            }
            // bricks
            for (int j = 0; j < Brick.getBricks().size(); j++) {
                Brick brick = Brick.getBricks().get(j);
                if(ball.intersects(brick) && brick.isVisible()){
                    // Top & Bottom
                    if((ball.getY() >= brick.getY() || ball.getY() <= brick.getY()) && !ball.isOnFire()){
                        ball.setYVelocity(-ball.getYVelocity());
                    }
                    // Right & Left
                    else if((ball.getY() >= brick.getX() || ball.getY() <= brick.getX()) && !ball.isOnFire()){
                        ball.setXVelocity(-ball.getXVelocity());
                    }
                    if(brick.getPrize() != null){
                        prizesToBeShown.add(brick.getPrize());
                    }
                    if((brick.getType() == Brick.TYPE.Wooden && brick.getShot() == 0) && !ball.isOnFire()){
                        System.out.println("first shot");
                        brick.collapse();
                    }
                    else{
                        Brick.getBricks().remove(j);
                        score++;
                        j --;
                    }
                }
            }
        }
        // prizes
        for (int i = 0; i < prizesToBeShown.size(); i++) {
            Prize tmp = prizesToBeShown.get(i);
            if(paddle.intersects(tmp)){
                System.out.println("using prize");
                tmp.usePrize();
//                prizesInUse.add(tmp);
                prizesToBeShown.remove(i);
                i--;
            }
            if(tmp.getY() >= SCREEN_HEIGHT){
                System.out.println("prize missed");
                prizesToBeShown.remove(i);
                i--;
            }
        }
        for (Brick brick : Brick.getBricks()) {
            if(brick.y > 650){
                running = false;
                gameOver(currentUser);
                break;
            }
        }
    }

    void checkPrizes(){

        for (int i = 0; i<prizesInUse.size(); i++) {
            Prize prize = prizesInUse.get(i);
            if(System.currentTimeMillis() - prize.lastUsed + prize.timeTaken >= 5000){
                System.out.println(prize.getPrize() + " inactivated!");
                prize.inactivatePrize();
                prizesInUse.remove(i);
                i--;
            }
        }

    }

    void restart(){

        Brick.getBricks().clear();
        balls.clear();
        prizesToBeShown.clear();
        prizesInUse.clear();
        lives = 3;
        score = 0;
        startGame();

    }

    void gameOver(User user){
        if(score > user.getMaxScore()){
            user.setMaxScore(score);
        }
        try {
            SaveNLoad.saveUser();
        } catch (Exception r){
            System.out.println("Caught an exception while saving users in game over");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running && started && !pauzed){
            move();
            checkCollisions();
            checkPrizes();
            checkRow();
        }
        repaint();
    }

    public boolean isNameRepeated(String name){
        String pathGame = ".//Resources//Games";
        File file = new File(pathGame);
        for (File file1 : Objects.requireNonNull(file.listFiles())) {
            if(file1.getName().equals(currentUser.getUserName() + name)){
                return true;
            }
        }
        return false;
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(isPaddleReversed){
                if (e.getKeyCode() == KeyEvent.VK_RIGHT && paddle.getX() - PADDLE_VELOCITY >= 0) {
                    paddle.setPaddleX((int) paddle.getX() - PADDLE_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && paddle.getX() - PADDLE_VELOCITY < 0) {
                    paddle.setPaddleX(0);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT && paddle.getX() + paddle.width + PADDLE_VELOCITY <= SCREEN_WIDTH) {
                    paddle.setPaddleX((int) paddle.getX() + PADDLE_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT && paddle.getX() + paddle.width + PADDLE_VELOCITY > SCREEN_WIDTH) {
                    paddle.setPaddleX(SCREEN_WIDTH - paddle.width);
                }
            }
            else {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT && paddle.getX() + paddle.width + PADDLE_VELOCITY <= SCREEN_WIDTH) {
                    paddle.setPaddleX((int) paddle.getX() + PADDLE_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && paddle.getX() + paddle.width + PADDLE_VELOCITY > SCREEN_WIDTH) {
                    paddle.setPaddleX(SCREEN_WIDTH - paddle.width);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT && paddle.getX() - PADDLE_VELOCITY >= 0) {
                    paddle.setPaddleX((int) paddle.getX() - PADDLE_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT && paddle.getX() - PADDLE_VELOCITY < 0) {
                    paddle.setPaddleX(0);
                }
            }
            if(e.getKeyChar() == 'p'){
                pauzed = !pauzed;
            }
            else if(e.getKeyCode() == 10 && !started){ // ENTER
                started = true;
                running = true;
            }
            else if(e.getKeyChar() == 'l' && !started){
                String name;
                do{
                    name = JOptionPane.showInputDialog("what is the name of the file");
                } while (name == null || name.length() == 0 || !isNameRepeated(name));
                try {
                    Brick.getBricks().clear();
                    balls.clear();
                    paddle = null;
                    SaveNLoad.loadGame(currentUser.getUserName(), name);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
            else if(e.getKeyChar() == 'r' && running){
                pauzed = true;
                int a = -10;
                a = JOptionPane.showConfirmDialog(GameFrame.getInstance() ,"Do you like to restart?");
                if(a == JOptionPane.YES_OPTION){
                    restart();
                }
                else{
                    pauzed = false;
                }
            }
            else if(e.getKeyChar() == 's' && running){
                pauzed = true;
                String name;
                do {
                    name = JOptionPane.showInputDialog("Write a name for your file");
                } while (name == null || name.length() == 0);
                int a = -10;
                if(isNameRepeated(name)){
                    a = JOptionPane.showConfirmDialog(GameFrame.getInstance(), "You have another game saved by this name, do you want to rewrite that?");
                }
                if(a == JOptionPane.YES_OPTION || a == -10) {
                    try {
                        SaveNLoad.saveGame(currentUser, name);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
            if(started && !running && e.getKeyCode() == 10){ // restart
                started = false;
                balls.clear();
                prizesToBeShown.clear();
                prizesInUse.clear();
                lives = 3;
                score = 0;
                Brick.getBricks().clear();
                startGame();
            }
        }
    }

}