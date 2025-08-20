package tiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];


    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[50]; //there are going to be up to 50 kinds of tiles in the game.
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; //to store the tile numbers for each position on the map.

        getTileImage();
        loadMap("/res/maps/worldV2.txt");
    }

    public void getTileImage() {
        //to load the images for the tiles
        // PLACEHOLDERS
        setup(0, "grass00", false);
        setup(1, "grass00", false);
        setup(2, "grass00", false);
        setup(3, "grass00", false);    
        setup(4, "grass00", false);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);
        // ACTUAL TILES
        //tiles start from index 10 so that single digit numbers aren't mixed with double digit numbers in the map file (makes it confusing)
        setup(10, "grass00", false);
        setup(11, "grass01", false);
        setup(12, "water00", true);
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water03", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);
        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);
        setup(39, "earth", false);
        setup(40, "wall", true);
        setup(41, "tree", true);




    } 
    public void setup(int index, String imageName, boolean collision) {
        //to set up the tile images and collision
        UtilityTool uTool = new UtilityTool();
        
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize); //scales the image to the tile size
            tile[index].collision = collision; 

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

                g2.drawImage(tile[tileNum].image, screenX, screenY, null); 

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
