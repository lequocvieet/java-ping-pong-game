package Main;
import Controller.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;


public class MainRun  {
    static Menu menu;
    static GamePanel game;
    static Help help;

    public static void main(String[] args) throws IOException  {
        GameFrame frame=new GameFrame();
        frame.setFocusable(true);
        menu=new Menu();
        frame.add(menu);
        frame.show();

        SwingUtilities.updateComponentTreeUI(frame);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (menu.counter == 1) {
                        frame.remove(menu);
                        game=new GamePanel();
                        frame.add(game);
                        game.requestFocusInWindow();


                    }
                    else if(menu.counter==2){
                       // frame.remove(menu);

                        //score goes Here

                    }

                    else if (menu.counter==3){
                        frame.remove(menu);
                        help=new Help();
                        frame.add(help);
                        help.requestFocusInWindow();


                        //option.add goes here
                    }
                }
                SwingUtilities.updateComponentTreeUI(frame);

            }
                @Override
                public void keyReleased (KeyEvent e){

                }

        });

    }
}
