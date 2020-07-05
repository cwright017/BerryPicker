package scripts.Nodes;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.WorldHopper;
import scripts.Utils.Utils;

public class HopWorld extends Node {
    @Override
    public boolean validate() {
        return Utils.isInBushArea() && Utils.getBushes().length == 0 && !Inventory.isFull();
    }

    @Override
    public void execute() {
        final int currentWorld = WorldHopper.getWorld();
        int world = WorldHopper.getRandomWorld(false, false);

        while(world == currentWorld) {
            world = WorldHopper.getRandomWorld(false, false);
        }

        WorldHopper.changeWorld(world);
    }
}
