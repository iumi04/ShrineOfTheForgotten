package objects;

import java.io.IOException;

import main.GamePanel;

public class OBJ_Door extends SuperObject{

    GamePanel gp;
    
    public OBJ_Door(GamePanel gp) {

        name = "Door";
        try {
            image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/objects/door.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
