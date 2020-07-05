package scripts.Nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import scripts.Utils.Constants;
import scripts.Utils.Utils;

public class WalkToArea extends Node {

    private boolean shouldBank() {
        return Inventory.isFull() && !Banking.isInBank();
    }

    private boolean shouldWalkToBush() {
        return !Inventory.isFull() && !Utils.isInBushArea();
    }

    private boolean walkToBank() {
        if (!WebWalking.walkToBank()) { // We failed to walk to the bank. Let's return false.
            return false;
        }

        return Timing.waitCondition(() -> Banking.isInBank(), General.random(8000, 9000));
    }

    private boolean walkToBushes() {
        if (!WebWalking.walkTo(Constants.bushArea.getRandomTile())) { // We failed to walk to the area. Let's return false.
            return false;
        }

        return Timing.waitCondition(() -> Utils.isInBushArea(), General.random(8000, 9000));
    }

    @Override
    public boolean validate() {
        if (shouldBank()) {
            return true; // Should bank.
        }

        if (shouldWalkToBush()) {
            return true;
        }

        return false;
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
