package Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.desktop.SystemSleepEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


public class GamePanel extends JPanel implements Runnable {
    static final  int  GAME_WIDTH=1000;
    static final int   GAME_HEIGHT=(int)(GAME_WIDTH*(0.5555));
    static final Dimension SCREEN_SIZE= new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIAMETER =30;
    static final int PADDLE_WIDTH=25;
    static final int PADDLE_HEIGHT=100;
    Thread gameThread;
    BufferedImage image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;
    public GamePanel(){
        newPaddle();
        newBall();
        score =new Score(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);
        gameThread=new Thread(this);
        gameThread.start();
    }
    public void newBall(){
        random=new Random();
        ball=new Ball((GAME_WIDTH/2-BALL_DIAMETER/2),(GAME_HEIGHT/2-BALL_DIAMETER/2),BALL_DIAMETER,BALL_DIAMETER);
    }
    public void newPaddle(){
    paddle1=new Paddle(0,(GAME_HEIGHT/2-PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1 );
    paddle2=new Paddle(GAME_WIDTH-PADDLE_WIDTH-1,(GAME_HEIGHT/2-PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
    }
    public void paint(Graphics g){
    File f=new File("C:\\Users\\Admin\\Desktop\\PingPongGame\\src\\Controller\\oldSchool.jpg");
        try {
            image=ImageIO.read(f);
            image=scaleImage(image,GAME_WIDTH,GAME_HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    graphics =image.getGraphics();//graphic nhan iamge lam graphic
    draw(graphics);
    g.drawImage(image,0,0,this);


    }
    public void draw(Graphics g){
    paddle1.draw(g);//ve hai paddle voi hai mau khac nhau
    paddle2.draw(g);  /// why it draw on screen?????
    ball.draw(g);
    }
    public void move(){
    paddle1.move();
    paddle2.move();
    ball.move();
    }
    public void checkCollision(){
    if(paddle1.y <= 0){
        paddle1.y=0;
    }
    if (paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT)){
        paddle1.y=GAME_HEIGHT-PADDLE_HEIGHT;
    }
        if(paddle2.y <= 0){
            paddle2.y=0;
        }
        if (paddle2.y >= (GAME_HEIGHT-PADDLE_HEIGHT)){
            paddle2.y=GAME_HEIGHT-PADDLE_HEIGHT;
        }
    if (ball.x<=0 ){
        ball.x=0;
        ball=new Ball(0,ball.y,ball.width,ball.height);

    }
    if(ball.y<=0){
        ball.y=0;
        ball=new Ball(ball.x,0,ball.width,ball.height);

    }
    }
    public BufferedImage scaleImage(BufferedImage oldImage,int width,int height){
    BufferedImage newImage= new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    Graphics2D G=newImage.createGraphics();
    G.drawImage(oldImage,0,0,width,height,null);
    G.dispose();
    return newImage;
    }
    public void run(){
        //game loop
        long lastTime=System.nanoTime();
        double amountOfTicks=60.0;
        double ns=1000000000/amountOfTicks;
        double delta=0;
        while(true){
            long now=System.nanoTime();
            delta+=(now-lastTime)/ns;
            lastTime=now;
            if(delta >=1){
                move();
                checkCollision();
                repaint();
                delta--;

            }

        }
        

    }
    public class AL extends KeyAdapter{

        public void keyPressed(KeyEvent e) {
        paddle1.KeyPressed(e);
        paddle2.KeyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
        paddle1.KeyRelease(e);
        paddle2.KeyRelease(e);

        }
    }

}

