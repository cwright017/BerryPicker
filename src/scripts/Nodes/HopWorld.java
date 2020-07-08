package scripts.Nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.WorldHopper;
import scripts.Berry;

public class HopWorld extends Node {
    private Berry berry;

    public HopWorld(Berry berry) {
        this.berry = berry;
    }

    @Override
    public boolean validate() {
        return berry.isInBushArea() && berry.getBushes().length == 0;
    }

    @Override
    public void execute() {
        final int currentWorld = WorldHopper.getWorld();
        int world = WorldHopper.getRandomWorld(false, false);

        while(world == currentWorld) {
            world = WorldHopper.getRandomWorld(false, false);
        }

        WorldHopper.changeWorld(world);

        Timing.waitCondition(() -> WorldHopper.getWorld() != currentWorld, General.random(5000, 8000));
    }
}
