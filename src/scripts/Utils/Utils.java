package scripts.Utils;

import org.tribot.api.General;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Utils {
    public static RSObject[] getBankBooths() {
        return Objects.findNearest(20, "Bank booth");
    }

    public static BufferedImage getImage(String name) {
        try {
            General.println("Loading image: " + name);
            return ImageIO.read(new URL(name));
        } catch (IOException e) {
            General.println(name + " not loaded. ");
        }
        return null;
    }
}
