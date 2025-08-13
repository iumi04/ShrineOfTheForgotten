package objects;

import java.io.IOException;

public class OBJ_Key extends SuperObject  {
    
    public OBJ_Key() {

        name = "Key";
        try {
            image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/objects/key.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
