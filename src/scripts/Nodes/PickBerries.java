package scripts.Nodes;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import scripts.Utils.Constants;
import scripts.Utils.Utils;

public class PickBerries extends Node {
    public static int berriesPicked = 0;
    public static int berriesInInv = 0;

    @Override
    public boolean validate() {
        return Utils.isInBushArea() && Utils.getBushes().length > 0;
    }

    @Override
    public void execute() {
        RSObject[] bushes = Utils.getBushes();
        RSItem[] initialBerriesInInv = Inventory.find(Constants.CADAVA_BERRY);

        if(bushes.length < 1) {
            return; // No bushes found.
        }

        if(!bushes[0].isOnScreen()) {
            if(!WebWalking.walkTo(bushes[0])) {
                return; // Failed to walk to bush.
            }

            if (!Timing.waitCondition(() -> bushes[0].isOnScreen(), General.random(8000, 9300))) {
                return; // Bush not on screen within 8 - 9.3s.
            }
        }

        if (!DynamicClicking.clickRSObject(bushes[0], "Pick-from")) {
            return; // Failed to click bush.
        }

        Timing.waitCondition(() -> {
            RSItem[] berries = Inventory.find(Constants.CADAVA_BERRY);
            if (berries != null) {
                berriesInInv = berries.length;

                return berries.length > initialBerriesInInv.length;
            }

            return false;
        }, General.random(3000, 6000));

        RSItem[] finalBerriesInInv = Inventory.find(Constants.CADAVA_BERRY);
        if (finalBerriesInInv != null && initialBerriesInInv != null) {
            berriesPicked += finalBerriesInInv.length - initialBerriesInInv.length;
        }
    }
}
