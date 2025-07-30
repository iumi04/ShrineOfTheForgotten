package main;

import java.awt.Color;
import java.awt.Dimension;
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

    Thread gameThread; //to ensure in-game time goes by the same as real life


    public GamePanel () {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    public void startGameThread() {

        gameThread = new Thread(this); //"this" here means this class, so GamePanel. 
        gameThread.start();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

        
    }

}
