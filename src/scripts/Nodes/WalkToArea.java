package scripts.Nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import scripts.Berry;
import scripts.Utils.Constants;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.walker_engine.WalkingCondition;

public class WalkToArea extends Node {
    private DaxWalker walker = DaxWalker.getInstance();
    private Berry berry;

    public WalkToArea(Berry berry) {
        this.berry = berry;
    }

    private boolean shouldBank() {
        return Inventory.isFull() && !Banking.isInBank();
    }

    private boolean shouldWalkToBush() {
        return !Inventory.isFull() && !berry.isPlayerInBushArea();
    }

    private void enableRun() {
        if(Game.getRunEnergy() > General.random(15, 35)) {
            if(!Options.isRunEnabled()) {
                Options.setRunEnabled(true);
            }
        }
    }

    private boolean walkToBank() {
        walker.getInstance().walkToBank(() -> {
            enableRun();
            if (!Banking.isInBank()) {
                return WalkingCondition.State.CONTINUE_WALKER;
            }
            return WalkingCondition.State.EXIT_OUT_WALKER_SUCCESS;
        });

        return Timing.waitCondition(() -> Banking.isInBank(), General.random(8000, 9000));
    }

    private boolean walkToBushes() {
        walker.walkTo(Constants.bushArea.getRandomTile(), () -> {
            enableRun();
            if (!berry.isPlayerInBushArea()) {
                return WalkingCondition.State.CONTINUE_WALKER;
            }
            return WalkingCondition.State.EXIT_OUT_WALKER_SUCCESS;
        });

        return Timing.waitCondition(() -> berry.isPlayerInBushArea(), General.random(8000, 9000));
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
        enableRun();

        if (shouldBank()) {
            walkToBank();
        } else {
            walkToBushes();
        }
    }
}
