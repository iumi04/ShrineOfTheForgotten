package objects;

import java.io.IOException;

public class OBJ_Boots extends SuperObject {
    
    public OBJ_Boots() {

        name = "Boots";
        try {
            image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/objects/boots.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
