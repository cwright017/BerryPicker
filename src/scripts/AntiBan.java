package scripts;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.types.RSObject;
import scripts.Debug.Debug;

public class AntiBan {
    private ABCUtil antiBan;
    private Debug debug = Debug.getInstance();
    private Berry berry;

    public AntiBan(Berry berry) {
        this.antiBan = new ABCUtil();
        this.berry = berry;
    }

    public void doTimedMethods() {
        if(antiBan.shouldLeaveGame()){
            debug.info("ANTIBAN - leaveGame");
            antiBan.leaveGame();
        }

        if(antiBan.shouldCheckTabs()) {
            debug.info("ANTIBAN - checkTabs");
            antiBan.checkTabs();
        }

        if(antiBan.shouldPickupMouse()) {
            debug.info("ANTIBAN - pickupMouse");
            antiBan.pickupMouse();
        }

    }

    public void hoverNextTarget() {
        berry.clearBushes();

        RSObject[] bushes = berry.getBushes();

        if(!Mouse.isInBounds()) {
            return; // Mouse off screen;
        }

        if(bushes == null || bushes.length < 1) {
            return; // No bushes
        }

        RSObject currentBush = bushes[0];

        if(currentBush.getID() == berry.BUSH_2) {
            return; // Current bush still has a berry on it
        }

        RSObject nextBush = (RSObject) antiBan.selectNextTarget(bushes);

        boolean isHoveringNextTarget = nextBush.getModel().getEnclosedArea().contains(Mouse.getPos());
        if(isHoveringNextTarget) {
            return; // Already hovering
        }

        if(antiBan.shouldHover()) {
            debug.info("ANTIBAN - hoverNextTarget");
            nextBush.hover();
        }

        General.sleep(General.random(800, 1200));
    }
}
