package Controller;

import javax.swing.*;
import java.awt.*;


public class GameFrame extends JFrame {
    public GameFrame() {


       this.setTitle("Pong Game");
        this.setSize(1012,670);
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Admin\\Desktop\\PingPongGame\\src\\Picture\\pong.png"));
       this.setLocationRelativeTo(null);//align the frame appear in the center

    }



}
