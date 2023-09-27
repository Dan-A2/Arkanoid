import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameFrame extends JFrame {

    private static GameFrame instanceGameFrame;

    @Override
    public void setContentPane(Container contentPane) {
        super.setContentPane(contentPane);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    GameFrame(){

//        this.add(new GamePanel());
        this.setTitle("Arkanoid");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

    public static GameFrame getInstance(){
        if(instanceGameFrame == null){
            instanceGameFrame = new GameFrame();
        }
        return instanceGameFrame;
    }

}