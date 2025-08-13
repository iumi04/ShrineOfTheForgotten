package objects;

import java.io.IOException;

public class OBJ_Chest extends SuperObject {
    
    public OBJ_Chest() {

        name = "Chest";
        try {
            image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/objects/chest (OLD).png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
