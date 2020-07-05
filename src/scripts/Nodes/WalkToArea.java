package scripts.Nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import scripts.Utils.Constants;
import scripts.Utils.Utils;

public class WalkToArea extends Node {

    private boolean shouldBank() {
        return Inventory.isFull();
    }

    private boolean walkToBank() {
        if (!WebWalking.walkToBank()) { // We failed to walk to the bank. Let's return false.
            return false;
        }

        return Timing.waitCondition(new Condition() { // If we reach the bank before the timeout, this method will return
            @Override public boolean active() {
                General.sleep(200, 300); // Reduces CPU usage.
                return Banking.isInBank();
            }
        }, General.random(8000, 9000));
    }

    private boolean walkToBushes() {
        if (!WebWalking.walkTo(Constants.bushArea.getRandomTile())) { // We failed to walk to the area. Let's return false.
            return false;
        }

        return Timing.waitCondition(new Condition() { // If we reach the bank before the timeout, this method will return
            @Override public boolean active() {
                General.sleep(200, 300); // Reduces CPU usage.
                return Utils.isInBushArea();
            }
        }, General.random(8000, 9000));
    }

    @Override
    public boolean validate() {
        if (shouldBank()) {
            return true; // Should bank.
        }

        return !Utils.isInBushArea(); // Player isn't in bush area
    }

    @Override
    public void execute() {
        if (shouldBank()) {
            walkToBank();
        } else {
            walkToBushes();
        }
    }
}
