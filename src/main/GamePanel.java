package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel; //this class inherits from GamePanel

import entity.Player;
import tiles.TileManager;

public class GamePanel extends JPanel implements Runnable{
    
    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tle
    final int scale = 3; // Important: modern computers have higher resolutions so 16x16 would be too small.

    public final int tileSize = originalTileSize * scale; //needs to be "public" when you want to access from other packages.
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12; //12x16 board
    public final int screenWidth = tileSize * maxScreenCol; //768 pixels
    public final int screenHeight  = tileSize * maxScreenRow; //576 pixels 

    // WORLD SETTINGS (50 x 50 grid)
    public final int maxWorldCol = 50; 
    public final int maxWorldRow = 50; 
    public final int worldWidth = tileSize * maxWorldCol; //1600 pixels
    public final int worldHeight = tileSize * maxWorldRow; //1200 pixels

    // FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread; //to ensure in-game time goes by the same as real life
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this,keyH);


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

        player.update();
    }
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2); //draws the tiles first so that the player is drawn on top of the tiles.
        player.draw(g2);
        g2.dispose(); //--> to save memory 
    }

}
