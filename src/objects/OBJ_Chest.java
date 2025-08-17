package objects;

import java.io.IOException;

import main.GamePanel;

public class OBJ_Chest extends SuperObject {

    GamePanel gp;
    
    public OBJ_Chest(GamePanel gp) {

        name = "Chest";
        try {
            image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/objects/chest (OLD).png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
