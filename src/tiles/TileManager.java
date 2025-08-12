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
    public Tile[] tile;
    public int mapTileNum[][];


    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[10]; //there are going to be 10 kinds of tiles in the game.
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; //to store the tile numbers for each position on the map.

        getTileImage();
        loadMap("/res/maps/world01.txt");
    }

    public void getTileImage() {

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass.png")); //grass image is put into a BufferedImage.
            
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/wall.png")); 
            tile[1].collision = true; 

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/water.png"));
            tile[2].collision = true; 

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/earth.png")); 
            
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tree.png")); 
            tile[4].collision = true; 

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/sand.png"));

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
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine(); //read a line from the file

                while (col < gp.maxWorldCol) {

                    String numbers[] = line.split(" "); 
                    int num = Integer.parseInt(numbers[col]); 
                    mapTileNum[col][row] = num; //store the number in the mapTileNum array
                    col++;
                }  
                if (col == gp.maxWorldCol) { 
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
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow]; //get the tile number from the mapTileNum array

            int worldX = worldCol * gp.tileSize; 
            int worldY = worldRow * gp.tileSize; //x and y position of the tile
            int screenX = worldX - gp.player.worldX + gp.player.screenX; 
            int screenY = worldY - gp.player.worldY + gp.player.screenY; //add player screenX and screenY to center the tile on the screen even when the player is at the corner (offset the difference logic).


            //only load what is visible to the player to save memory and not make the game laggy.
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                //if the tile is within the player's view (+-1 tile to remove the black background/border)

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null); 

            } 

            worldCol++;

            if (worldCol == gp.maxWorldCol) { 
                //if we reach the end of the row
                worldCol = 0; 
                worldRow++; //move to next row
            }
        }
    }


}
