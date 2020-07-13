package scripts;

import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import scripts.Debug.Debug;
import scripts.Utils.Constants;

public class Berry {
    public RSArea AREA = Constants.bushArea;

    public int BUSH_2;
    public int BUSH_1;

    public int ID;
    public String NAME;

    public String SRC;

    public int totalInBank = 0;
    public int totalInInv = 0;
    public int totalCollected = 0;

    private int distanceToSearch = 15;

    private Debug debug = Debug.getInstance();

    private RSObject[] bushes;

    public Berry(Constants.Berries type) {
        switch (type) {
            case REDBERRY:
                BUSH_2 = Constants.REDBERRRY_BUSH_2;
                BUSH_1 = Constants.REDBERRRY_BUSH_1;

                ID = Constants.REDBERRY;
                NAME = "Redberry";

                SRC = Constants.REDBERRY_SRC;
                break;

            case CADAVA_BERRY:
                BUSH_2 = Constants.CADAVA_BERRY_BUSH_2;
                BUSH_1 = Constants.CADAVA_BERRY_BUSH_1;

                ID = Constants.CADAVA_BERRY;
                NAME = "Cadava Berry";

                SRC = Constants.CADAVA_BERRY_SRC;
                break;
        }
    }

    public RSObject[] getBushes() {
        bushes = Objects.findNearest(
                    distanceToSearch,
                    (bush -> (bush.getID() == BUSH_2 || bush.getID() == BUSH_1)));

        return bushes;
    }

    public void clearBushes() {
        bushes = null;
    }

    public boolean isPlayerInBushArea() {
        return AREA.contains(Player.getPosition());
    }

}
