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
    static final int   GAME_HEIGHT=(int)(GAME_WIDTH*(0.5555));//Same as the real table in ping pong original game
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
        score=new Score(GAME_WIDTH,GAME_HEIGHT);

        this.setFocusable(true);         //gamepanel already on focus mode
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
    File f=new File("C:\\Users\\Admin\\Desktop\\PingPongGame\\src\\Picture\\oldSchool.jpg");
        try {
            image=ImageIO.read(f);
            image=scaleImage(image,GAME_WIDTH,GAME_HEIGHT);//scale new image to fit the size of origin image
        } catch (IOException e) {
            e.printStackTrace();
        }

    graphics =image.getGraphics();
    draw(graphics);//only draw 2 recktangle and ball into image's graphic
    g.drawImage(image,0,0,this);//draw image into game panel
    }

    public void draw(Graphics g){
    paddle1.draw(g);
    paddle2.draw(g);
    ball.draw(g);
    score.draw(g);

    }

    public void move(){
    paddle1.move();
    paddle2.move();
    ball.move();
    }
    public void checkCollision() {
       //ensure that paddle always on screen

        if (paddle1.y <= 0) {
            paddle1.y = 0;
        }
        if (paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) {
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }
        if (paddle2.y <= 0) {
            paddle2.y = 0;
        }
        if (paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) {
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }

        //  bounce ball off top and bottom of the window's edges

        if (ball.y <= 0) {
            ball.setYDirection(-ball.yVelocity);

        }

        if (ball.y >= GAME_HEIGHT - ball.height) {
            ball.setYDirection(-ball.yVelocity);
        }

        //bounce ball off the paddle
        //using method intersects because ball inherits rectangle class

        if(ball.intersects(paddle1) ){
           // ball.xVelocity=Math.abs(ball.xVelocity);
           // ball.xVelocity++;
            if(ball.yVelocity>0){
                //ball.yVelocity++;
            }
           // else ball.yVelocity--;

            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        if(ball.intersects(paddle2) ){
            //ball.xVelocity++;
            if(ball.yVelocity>0){
              //  ball.yVelocity++;
            }
            //else ball.yVelocity--;

            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

    // give player 1 point and creates a new paddle & ball

        if (ball.x <= 0){
          score.player2++;
          newPaddle();
          newBall();

        }

        if (ball.x >= GAME_WIDTH-BALL_DIAMETER){
            score.player1++;
            newPaddle();
            newBall();

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
        long lastUpdate=System.nanoTime();
        double fps=120.0;//exactly 60 frame per second
        // So 60 frame per 10^9 nanosecond
        double updateRateInNanoSecond=1000000000/fps;   //speed update( we caculate 1 frame takes how much time)
        double accumulator=0;
        while(true){
            long now=System.nanoTime();
            double lastRenderTime=(now-lastUpdate);//caculate time spend to render
            accumulator+=lastRenderTime;
            lastUpdate=now;
            if(accumulator >= updateRateInNanoSecond){
                move();
                checkCollision();
                repaint();
                accumulator-=updateRateInNanoSecond;
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

