package scripts.Utils;

import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;
import scripts.Debug.Debug;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Utils {
    public static RSObject[] getBankBooths() {
        return Objects.findNearest(20, "Bank booth");
    }
    private static Debug debug = Debug.getInstance();

    public static BufferedImage getImage(String name) {
        try {
            debug.log("Loading image: " + name);
            return ImageIO.read(new URL(name));
        } catch (IOException e) {
            debug.error(name + " not loaded. ");
        }
        return null;
    }
}
