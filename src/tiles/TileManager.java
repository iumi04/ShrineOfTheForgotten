package tiles;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];


    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[10]; //there are going to be 10 kinds of tiles in the game.
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow]; //to store the tile numbers for each position on the map.

        getTileImage();
        loadMap("/res/maps/map01.txt");
    }

    public void getTileImage() {

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass.png")); //grass image is put into a BufferedImage.
            
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/wall.png")); 

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/water.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath) {

        try {
            InputStream is = getClass().getResourceAsStream(filePath); //to import the map file
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); //used to read the file line by line

            int col = 0;
            int row = 0;
            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {

                String line = br.readLine(); //read a line from the file

                while (col < gp.maxScreenCol) {

                    String numbers[] = line.split(" "); 
                    int num = Integer.parseInt(numbers[col]); 
                    mapTileNum[col][row] = num; //store the number in the mapTileNum array
                    col++;
                }  
                if (col == gp.maxScreenCol) { 
                    //if we reach the end of the row
                    col = 0; 
                    row++; //move to next 
                }
            }
            br.close();


        } catch (Exception e) {
          
        }
    }
    public void draw(Graphics2D g2) {
        
        // g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null); draws the grass tile at (0,0) with the size of tileSize.
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {

            int tileNum = mapTileNum[col][row]; //get the tile number from the mapTileNum array

            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null); 
            col++;
            x += gp.tileSize; //move to the next column

            if (col == gp.maxScreenCol) { 
                //if we reach the end of the row
                col = 0; 
                x = 0; 
                row++; //move to next row
                y += gp.tileSize; //move down by tile size
            }
        }
    }


}
