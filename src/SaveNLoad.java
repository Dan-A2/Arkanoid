import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class SaveNLoad {

    private static final String pathGame = ".//Resources//Games";
    private static final String pathUsers = ".//Resources//Users";

    public static void saveGame(User user, String name) throws IOException {
        File file = new File(pathGame + "//" + user.getUserName() + name);
        if(file.exists()){
            file.delete();
        }
        file.createNewFile();
        PrintStream printStream = new PrintStream(new FileOutputStream(file, true));
        printStream.println(GamePanel.score + " " + GamePanel.lives);
        printStream.println(Brick.getBricks().size());
        for (Brick brick : Brick.getBricks()) {
            printStream.print(brick.getX() + " " + brick.getY() + " " + brick.getShot() + " " + brick.getType());
            if(brick.getPrize() != null){
                printStream.print(" " + "sadat" +  " " + brick.getPrize().getX() + " " + brick.getPrize().getY() + " " + brick.getPrize().width + " " + brick.getPrize().height + " " + brick.getPrize().getPrize());
            }
            else{
                printStream.print(" " + null);
            }
            printStream.println();
        }
        printStream.println(GamePanel.balls.size());
        for (Ball ball : GamePanel.balls){
            double xv = ball.getXVelocity(), yv = ball.getYVelocity();
            if(xv > 3){
                xv = 3;
            }
            else if(xv < -3){
                xv = -3;
            }
            if(yv > 4){
                yv = 4;
            }
            else if(yv < -4){
                yv = -4;
            }
            printStream.println(ball.getX() + " " + ball.getY() + " " + xv + " " + yv + " " + ball.isOnFire());
        }
        Paddle paddle = GamePanel.paddle;
        printStream.println(paddle.getX() + " " + paddle.getY());
        printStream.println(GamePanel.prizesInUse.size());
        for (Prize prize : GamePanel.prizesInUse) {
            printStream.println(prize.getPrize() + " " + (System.currentTimeMillis() - prize.lastUsed + prize.timeTaken));
        }
        printStream.println(GamePanel.prizesToBeShown.size());
        for (Prize prize : GamePanel.prizesToBeShown) {
            printStream.println(prize.getPrize() + " " + prize.x + " " + prize.y);
        }
    }

    public static void saveUser() throws IOException {
        File file = new File(pathUsers + "//users");
        file.delete();
        file.createNewFile();
        PrintStream printStream = new PrintStream(new FileOutputStream(file, true));
        for (User user : User.getAllUsers()) {
            printStream.println(user.getUserName() + " " + user.getMaxScore());
        }
    }

    public static void loadGame(String username, String name) throws FileNotFoundException {
        File file = new File(pathGame);
        for (File file1 : Objects.requireNonNull(file.listFiles())) {
            if(file1.getName().equals(username + name)){
                Scanner scanner = new Scanner(file1);
                int score = scanner.nextInt();
                int lives = scanner.nextInt();
                GamePanel.lives = lives;
                GamePanel.score = score;
                scanner.nextLine();
                int size = Integer.parseInt(scanner.next());
                scanner.nextLine();
                for (int i = 0; i < size; i++) {
                    double x = Double.parseDouble(scanner.next());
                    double y = Double.parseDouble(scanner.next());
                    int shot = Integer.parseInt(scanner.next());
                    String type = scanner.next();
                    String prize = scanner.next();
                    Brick brick = new Brick(Brick.getW(), Brick.getH(), (int) x, (int) y);
                    brick.setShot(shot);
                    switch (type){
                        case "Glassy":
                            brick.setType(Brick.TYPE.Glassy);
                            break;
                        case "Wooden":
                            brick.setType(Brick.TYPE.Wooden);
                            break;
                        case "Blinking":
                            brick.setType(Brick.TYPE.Blinking);
                            break;
                        case "Invisible":
                            brick.setType(Brick.TYPE.Invisible);
                            break;
                        case "ContainPrize":
                            brick.setType(Brick.TYPE.ContainPrize);
                            break;
                    }
                    if(prize.equals("sadat")){
                        double x2 = Double.parseDouble(scanner.next());
                        double y2 = Double.parseDouble(scanner.next());
                        int width = Integer.parseInt(scanner.next());
                        int height = Integer.parseInt(scanner.next());
                        String prize2 = scanner.next();
                        switch (prize2){
                            case "FireBall":
                                brick.setPrize(new Prize(width, height, (int) x2,(int) y2, Prize.PRIZE.FireBall, Prize.getFireBall()));
                                break;
                            case "MultipleBalls":
                                brick.setPrize(new Prize(width, height, (int) x2,(int) y2, Prize.PRIZE.MultipleBalls, Prize.getMultipleBalls()));
                                break;
                            case "FastBall":
                                brick.setPrize(new Prize(width, height, (int) x2,(int) y2, Prize.PRIZE.FastBall, Prize.getFastBall()));
                                break;
                            case "SlowBall":
                                brick.setPrize(new Prize(width, height, (int) x2,(int) y2, Prize.PRIZE.SlowBall, Prize.getSlowBall()));
                                break;
                            case "BiggerPaddle":
                                brick.setPrize(new Prize(width, height, (int) x2,(int) y2, Prize.PRIZE.BiggerPaddle, Prize.getBigPaddle()));
                                break;
                            case "SmallerPaddle":
                                brick.setPrize(new Prize(width, height, (int) x2,(int) y2, Prize.PRIZE.SmallerPaddle, Prize.getSmallPaddle()));
                                break;
                            case "DazyPaddle":
                                brick.setPrize(new Prize(width, height, (int) x2,(int) y2, Prize.PRIZE.DazyPaddle, Prize.getDazyPaddle()));
                                break;
                            case "Random":
                                brick.setPrize(new Prize(width, height, (int) x2,(int) y2, Prize.PRIZE.Random, Prize.getRandom()));
                                break;
                        }
                    }
                    brick.setVisible(true);
                    Brick.getBricks().add(brick);
                    scanner.nextLine();
                }
                int ballsCount = Integer.parseInt(scanner.next());
                for (int i = 0; i < ballsCount; i++) {
                    double x = Double.parseDouble(scanner.next());
                    double y = Double.parseDouble(scanner.next());
                    double speedx = Double.parseDouble(scanner.next());
                    double sppedy = Double.parseDouble(scanner.next());
                    scanner.nextLine();
                    Ball ball = new Ball((int) x,(int) y, 10, 10);
                    ball.setXVelocity(speedx);
                    ball.setYVelocity(sppedy);
                    GamePanel.balls.add(ball);
                }
                double paddleX = Double.parseDouble(scanner.next());
                double paddleY = Double.parseDouble(scanner.next());
                GamePanel.paddle = new Paddle(15, 60, (int) paddleX, (int) paddleY);
                scanner.nextLine();
                int count1 = Integer.parseInt(scanner.next());
                for (int i = 0; i < count1; i++) {
                    String prizeType = scanner.next();
                    String timeT = scanner.next();
                    Prize prize = null;
                    switch (prizeType){
                        case "FireBall":
                            prize = new Prize(10, 10, 10, 10, Prize.PRIZE.FireBall, Prize.getFireBall());
                            break;
                        case "MultipleBalls":
                            prize = new Prize(10, 10, 10, 10, Prize.PRIZE.MultipleBalls, Prize.getMultipleBalls());
                            break;
                        case "FastBall":
                            prize = new Prize(10, 10, 10, 10, Prize.PRIZE.FastBall, Prize.getFastBall());
                            break;
                        case "SlowBall":
                            prize = new Prize(10, 10, 10, 10, Prize.PRIZE.SlowBall, Prize.getSlowBall());
                            break;
                        case "BiggerPaddle":
                            prize = new Prize(10, 10, 10, 10, Prize.PRIZE.BiggerPaddle, Prize.getBigPaddle());
                            break;
                        case "SmallerPaddle":
                            prize = new Prize(10, 10, 10, 10, Prize.PRIZE.SmallerPaddle, Prize.getSmallPaddle());
                            break;
                        case "DazyPaddle":
                            prize = new Prize(10, 10, 10, 10, Prize.PRIZE.DazyPaddle, Prize.getDazyPaddle());
                            break;
                        case "Random":
                            prize = new Prize(10, 10, 10, 10, Prize.PRIZE.Random, Prize.getRandom());
                            break;
                    }
                    prize.timeTaken = Long.parseLong(timeT);
                    prize.lastUsed = System.currentTimeMillis();
                    prize.usePrize();
                    GamePanel.prizesInUse.add(prize);
                    scanner.nextLine();
                }
                int count2 = Integer.parseInt(scanner.next());
                for (int i = 0; i < count2; i++) {
                    String prizeType = scanner.next();
                    int x = Integer.parseInt(scanner.next());
                    int y = Integer.parseInt(scanner.next());
                    Prize prize = null;
                    switch (prizeType){
                        case "FireBall":
                            prize = new Prize(10, 10, x, y, Prize.PRIZE.FireBall, Prize.getFireBall());
                            break;
                        case "MultipleBalls":
                            prize = new Prize(10, 10, x, y, Prize.PRIZE.MultipleBalls, Prize.getMultipleBalls());
                            break;
                        case "FastBall":
                            prize = new Prize(10, 10, x, y, Prize.PRIZE.FastBall, Prize.getFastBall());
                            break;
                        case "SlowBall":
                            prize = new Prize(10, 10, x, y, Prize.PRIZE.SlowBall, Prize.getSlowBall());
                            break;
                        case "BiggerPaddle":
                            prize = new Prize(10, 10, x, y, Prize.PRIZE.BiggerPaddle, Prize.getBigPaddle());
                            break;
                        case "SmallerPaddle":
                            prize = new Prize(10, 10, x, y, Prize.PRIZE.SmallerPaddle, Prize.getSmallPaddle());
                            break;
                        case "DazyPaddle":
                            prize = new Prize(10, 10, x, y, Prize.PRIZE.DazyPaddle, Prize.getDazyPaddle());
                            break;
                        case "Random":
                            prize = new Prize(10, 10, x, y, Prize.PRIZE.Random, Prize.getRandom());
                            break;
                    }
                    GamePanel.prizesToBeShown.add(prize);
                    scanner.nextLine();
                }
                scanner.close();
            }
        }
    }

    public static void loadUsers() throws FileNotFoundException {
        File kooft = new File(pathUsers);
        for (File file : Objects.requireNonNull(kooft.listFiles())) {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()){
                String username = scanner.next();
                String maxScore = scanner.next();
                scanner.nextLine();
                User tmp = new User(username);
                tmp.setMaxScore(Integer.parseInt(maxScore));
            }
            scanner.close();
        }
    }

}