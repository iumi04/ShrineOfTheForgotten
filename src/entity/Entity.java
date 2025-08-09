package entity;

import java.awt.image.BufferedImage;

public class Entity {
    
    public int x, y;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; //used to store the image files
    public String direction;

    public int spriteCounter = 0; //used to change the sprite every few frames
    public int spriteNum = 1; //used to change the sprite every few frames
}
