package scripts.Nodes;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSObject;
import scripts.Utils.Utils;

public class PickBerries extends Node {
    public static int berriesPicked = 0;

    @Override
    public boolean validate() {
        return Utils.isInBushArea();
    }

    @Override
    public void execute() {
        RSObject[] bushes = Utils.getBushes();

        if(bushes.length < 1) {
            return; // No bushes found.
        }

        if(!bushes[0].isOnScreen()) {
            if(!WebWalking.walkTo(bushes[0])) {
                return; // Failed to walk to bush.
            }

            if (!Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100); // Sleep to reduce CPU usage.
                    return bushes[0].isOnScreen();
                }
            }, General.random(8000, 9300))) {
                return; // Bush not on screen within 8 - 9.3s.
            }
        }

        if (!DynamicClicking.clickRSObject(bushes[0], "Pick-from")) {
            return; // Failed to click bush.
        }

        berriesPicked++;
    }
}
