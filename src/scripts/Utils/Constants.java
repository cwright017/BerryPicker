package scripts.Utils;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import java.awt.*;

public class Constants {
    public static final RSArea bushArea = new RSArea(
            new RSTile(3268, 3375, 0),
            new RSTile(3278, 3366, 0)
    );

    public static final int CADAVA_BERRY_BUSH_2 = 23625;
    public static final int CADAVA_BERRY_BUSH_1 = 23626;

    public static final int REDBERRRY_BUSH_2 = 23628;
    public static final int REDBERRRY_BUSH_1 = 23629;

    public static final int CADAVA_BERRY = 753;
    public static final int REDBERRY = 1951;

    public static final String CADAVA_BERRY_SRC = "https://i.imgur.com/ltFaqsE.png";
    public static final String REDBERRY_SRC = "https://i.imgur.com/Uhm2Oyf.png";

    public static final Color PAINT_COLOR = new Color(207,56,178);
    public static final Color PAINT_BG_COLOR = new Color(187,169,137);
}
