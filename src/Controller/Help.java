package Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Help extends JPanel{

    private BufferedImage image;


    public Help() {


        try {
            image = ImageIO.read(new File("C:\\Users\\Admin\\Desktop\\PingPongGame\\src\\Picture\\Ponghelp.png"));
        } catch (IOException ex) {
            // handle exception...
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters
    }


}
