package scripts.Utils;

import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;

public class Utils {
    public static RSObject[] getBankBooths() {
        return Objects.findNearest(20, "Bank booth");
    }
}
