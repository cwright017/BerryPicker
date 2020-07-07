package scripts.Utils;

import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;

public class Utils {
    public static RSObject[] getBushes() {
        return Objects.findNearest(
                5,
                (cadava -> (cadava.getID() == Constants.CADAVA_BERRY_BUSH_2 || cadava.getID() == Constants.CADAVA_BERRY_BUSH_1)));
    }

    public static boolean isInBushArea() {
        return Constants.bushArea.contains(Player.getPosition());
    }

    public static RSObject[] getBankBooths() {
        return Objects.findNearest(20, "Bank booth");

    }
    public static boolean isInBank() {
        final RSObject[] booths = getBankBooths();

        if(booths.length > 1) {
            return booths[0].isOnScreen();
        }

        return false;
    }
}
