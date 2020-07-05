package scripts.Utils;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import java.awt.*;

public class Constants {
    public static final RSArea bushArea = new RSArea(
            new RSTile(3264, 3371, 0),
            new RSTile(3271, 3367, 0)
    );

    public static final int bushID2 = 23625;
    public static final int bushID1 = 23626;

    public static final int CADAVA_BERRY = 753;

    public static final Color PAINT_COLOR = new Color(207,56,178);
    public static final Color PAINT_BG_COLOR = new Color(187,169,137);
}