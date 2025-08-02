package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel; //this class inherits from GamePanel

public class GamePanel extends JPanel implements Runnable{
    
    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tle
    final int scale = 3; // Important: modern computers have higher resolutions so 16x16 would be too small.

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12; //12x16 board
    final int screenWidth = tileSize * maxScreenCol; //768 pixels
    final int screenHeight  = tileSize * maxScreenRow; //576 pixels 

    // FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread; //to ensure in-game time goes by the same as real life


    // Set Player's Default Position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel () {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true); //allows the game panel to "focus" on key inputs.
    }

    public void startGameThread() {

        gameThread = new Thread(this); //"this" here means this class, so GamePanel. 
        gameThread.start();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {
            //as long as this game thread exists..

            // 1 UPDATE: update information such as character positions
            update();
            // 2 DRAW: draw the screen with the newly updated information
            repaint(); //--> this is how you call the paintComponent method.

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000; // --> .sleep() only accepts miliseconds in param.

                if (remainingTime < 0) {
                    remainingTime = 0; //in the case that repaint takes more time than the drawInterval/exceeded remainingtime. 
                }

                Thread.sleep((long) remainingTime); //pauses the game loop so that it won't do anything until the sleep is over.

                nextDrawTime += drawInterval; 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }
    public void update() {

        if (keyH.upPressed == true) {
            playerY -= playerSpeed;
        }
        else if (keyH.downPressed == true) {
            playerY += playerSpeed;
        }
        else if (keyH.leftPressed == true) {
            playerX -= playerSpeed;
        }
        else if (keyH.rightPressed == true) {
            playerX += playerSpeed;
        }
    }
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose(); //--> to save memory 
    }

}
