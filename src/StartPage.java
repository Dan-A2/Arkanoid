import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

public class StartPage extends JPanel implements ActionListener {

//    JFrame frame = new JFrame();
    JButton start = new JButton();
    JButton load = new JButton();
    JButton leaderBoard = new JButton();

    StartPage(){

        Border border = BorderFactory.createLineBorder(Color.orange, 5);

        start.setBounds(250, 100, 100, 50);
        start.addActionListener(this);
        start.setBackground(Color.black);
        start.setText("Start Game");
        start.setForeground(Color.ORANGE);
        start.setFocusable(false);
        start.setBorder(BorderFactory.createEtchedBorder());

//        load.setBounds(250, 175, 100, 50);
//        load.addActionListener(this);
//        load.setBackground(Color.black);
//        load.setText("Load Game");
//        load.setForeground(Color.ORANGE);
//        load.setFocusable(false);
//        load.setBorder(BorderFactory.createEtchedBorder());

        leaderBoard.setBounds(250, 250, 100, 50);
        leaderBoard.addActionListener(this);
        leaderBoard.setBackground(Color.black);
        leaderBoard.setText("Leaderboard");
        leaderBoard.setForeground(Color.ORANGE);
        leaderBoard.setFocusable(false);
        leaderBoard.setBorder(BorderFactory.createEtchedBorder());

        try {
            SaveNLoad.loadUsers();
        } catch (Exception e){}

        JLabel label = new JLabel();
        label.setText("What do you like to do?");
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setForeground(Color.red);
        label.setFont(new Font("MV Boli", Font.PLAIN, 20));
        label.setBackground(new Color(0x123456));
        label.setOpaque(true);
        label.setBorder(border);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(150, 400, 300, 100);

//        frame.setTitle("WELCOME!");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setPreferredSize(new Dimension(600, 600));
        this.setBackground(new Color(0x123456));
//        frame.setResizable(false);
//        frame.setVisible(true);
//        frame.setLocationRelativeTo(null);
        this.add(start);
        this.add(load);
        this.add(leaderBoard);
        this.add(label);
        GameFrame.getInstance().setContentPane(this);
        GameFrame.getInstance().pack();

    }

    static String getName2(){
        String name;
        do {
            name = JOptionPane.showInputDialog("enter your name ");
        } while (name == null || name.length() == 0);
        return name;
    }

    static void showLeaderboard(){
        if(User.getAllUsers().size() > 0) {
            StringBuilder kooft = new StringBuilder();
            for (int i = 0; i < User.getAllUsers().size()-1; i++) {
                for (int j = i+1; j < User.getAllUsers().size(); j++) {
                    if(User.getAllUsers().get(j).getMaxScore() > User.getAllUsers().get(i).getMaxScore()){
                        Collections.swap(User.getAllUsers(), i, j);
                    }
                }
            }
            for (User user : User.getAllUsers()) {
                kooft.append(user.getUserName() + " : " + user.getMaxScore() + System.lineSeparator());
            }
            JOptionPane.showMessageDialog(GameFrame.getInstance(), kooft.toString(), "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(GameFrame.getInstance(), "No players yet!", "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public boolean isNameThere(String name){
        String pathGame = ".//Resources//Games";
        File file = new File(pathGame);
        for (File file1 : Objects.requireNonNull(file.listFiles())) {
            if(file1.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == start){
//            frame.dispose();
//            new GameFrame();
            String username = getName2();
            boolean flag = false;
            User thisUser = null;
            for (User user : User.getAllUsers()) {
                if(user.getUserName().equals(username)){
                    flag = true;
                    thisUser = user;
                    break;
                }
            }
            if(!flag) {
                thisUser = new User(username);
            }
            GamePanel panel = null;
            try {
                panel = new GamePanel(thisUser);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            GameFrame.getInstance().setContentPane(panel);
            panel.requestFocus();
        }
        else if(e.getSource() == leaderBoard){
            showLeaderboard();
        }

    }
}