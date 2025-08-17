package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;

// import javafx.scene.paint.Color;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    
    GamePanel gp;
    KeyHandler keyH;

    //to center the player on the screen  
    public final int screenX; 
    public final int screenY; 
    public int hasKey = 0; 
    int standCounter = 0; //to control the standing still animation

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        //so that the player is always in the center of the screen
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32); //the size of the solid area for collision detection
        solidAreaDefaultX = solidArea.x; 
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {

        worldX = gp.tileSize * 23; //initial position of the player
        worldY = gp.tileSize * 21; //initial position of the player
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage() {
        //to load the images
        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_right_2.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (keyH.upPressed == true || keyH.downPressed == true || 
            keyH.leftPressed == true || keyH.rightPressed == true) {

            if (keyH.upPressed == true) {
                direction = "up";
            }
            else if (keyH.downPressed == true) {
                direction = "down";
            }
            else if (keyH.leftPressed == true) {
                direction = "left";
            }
            else if (keyH.rightPressed == true) {
                direction = "right";
            }

            // CHECK COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (collisionOn == false) {
                switch(direction) {
                    case "up": 
                        worldY -= speed; break;
                    case "down": 
                        worldY += speed; break;
                    case "left": 
                        worldX -= speed; break;
                    case "right": 
                        worldX += speed; break;
                }
            }

            // sprite counter
            spriteCounter++;
            if (spriteCounter > 12) {
                // every 12 frames, change the sprite
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0; //reset the counter

            }
        }
        else {
            standCounter++;
            if (standCounter == 30) { //if the player is standing still for 20 frames
                spriteNum = 1; 
                standCounter = 0; 
            }

        }

    }
    public void pickUpObject(int index) {

        if (index != 999) { //if the index is not 999, the player has collided with an object.
            
            String objectName = gp.obj[index].name;
            
            switch (objectName) {
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[index] = null; //remove the object from the game
                    gp.ui.showMessage("You got a key!"); 
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.playSE(3); //play the unlock sound effect
                        gp.obj[index] = null; 
                        gp.ui.showMessage("You opened the door!");
                        hasKey--; //use a key to open the door
                    }
                    else {
                        gp.ui.showMessage("You need a key to open this door!");
                    }
                    break;
                case "Boots":
                    gp.playSE(2);
                    speed += 1;
                    gp.obj[index] = null; 
                    gp.ui.showMessage("Speed Up!");
                    break;
                case "Chest":
                    gp.ui.gameFinished = true; 
                    gp.stopMusic(); 
                    gp.playSE(4); 
                    break;
            }
            
        }
    }

    public void draw(Graphics2D g2) {

        // g2.setColor(Color.white);
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize); >>>>>>>> rectangle (original testing)

        BufferedImage image = null;

        switch(direction) {
        case "up":
            if (spriteNum == 1) {
                image = up1;
            }
            if (spriteNum == 2) {
                image = up2;
            }
            break;
        case "down":
            if (spriteNum == 1) {
                image = down1;
            }
            if (spriteNum == 2) {
                image = down2;
            }
            break;
        case "left":
            if (spriteNum == 1) {
                image = left1;
            }
            if (spriteNum == 2) {
                image = left2;
            }
            break;
        case "right":
            if (spriteNum == 1) {
                image = right1;
            }
            if (spriteNum == 2) {
                image = right2;
            }
            break; 
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

}
