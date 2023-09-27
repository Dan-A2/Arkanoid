import java.util.LinkedList;

public class User {

    private final static LinkedList<User> allUsers = new LinkedList<>();
    private String userName;
    private int maxScore;

    public User(String username){
        this.userName = username;
        this.maxScore = -2000;
        allUsers.add(this);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public static LinkedList<User> getAllUsers() {
        return allUsers;
    }
}