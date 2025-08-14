package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; //used to store the image files
    public String direction;

    public int spriteCounter = 0; //used to change the sprite every few frames
    public int spriteNum = 1; //used to change the sprite every few frames

    public Rectangle solidArea; //used to detect collisions
    public int solidAreaDefaultX, solidAreaDefaultY; //used to reset the solid area position
    public boolean collisionOn = false;
}
