package objects;

import java.io.IOException;

public class OBJ_Door extends SuperObject{
    
    public OBJ_Door() {

        name = "Door";
        try {
            image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/objects/door.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
