package scripts.Nodes;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import scripts.AntiBan;
import scripts.Berry;
import scripts.Debug.Debug;

public class PickBerries extends Node {
    private Berry berry;
    private AntiBan antiBan;
    private Debug debug = Debug.getInstance();

    public PickBerries(Berry berry, AntiBan antiBan) {
        this.antiBan = antiBan;
        this.berry = berry;
    }

    @Override
    public boolean validate() {
        return berry.isPlayerInBushArea() && berry.getBushes().length > 0;
    }

    @Override
    public void execute() {
        RSObject[] bushes = berry.getBushes();
        RSItem[] initialBerriesInInv = Inventory.find(berry.ID);

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
            antiBan.hoverNextTarget();

            RSItem[] berries = Inventory.find(berry.ID);
            if (berries != null) {
                berry.totalInInv = berries.length;
                return berries.length > initialBerriesInInv.length;
            }

            return false;
        }, General.random(3000, 6000));

        RSItem[] finalBerriesInInv = Inventory.find(berry.ID);
        if (finalBerriesInInv != null && initialBerriesInInv != null) {
            berry.totalCollected += finalBerriesInInv.length - initialBerriesInInv.length;
        }
    }
}
