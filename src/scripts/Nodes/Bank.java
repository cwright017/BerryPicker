package scripts.Nodes;

import com.sun.tools.javah.Gen;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import scripts.Utils.Constants;
import scripts.Utils.Utils;

public class Bank extends Node {
    public static int totalBerries = 0;

    @Override
    public boolean validate() {
        return Utils.isInBank() && Inventory.isFull();
    }

    @Override
    public void execute() {
        RSObject booth = Utils.getBankBooths()[0];

        if(Banking.isBankScreenOpen()) {
            RSItem[] totalItemBank = Banking.find(Constants.CADAVA_BERRY);
            if(totalItemBank != null) {
                totalBerries = totalItemBank[0].getStack();
            }

            Banking.depositAll();

            if(!Timing.waitCondition(() -> Inventory.isFull(), General.random(3000, 4000))) {
                return;
            }
        }

        if (!DynamicClicking.clickRSObject(booth, "Bank")) {
            return; // Failed to bank.
        }
    }
}
