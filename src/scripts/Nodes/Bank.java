package scripts.Nodes;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import scripts.Berry;
import scripts.Utils.Utils;

public class Bank extends Node {
    private Berry berry;

    public Bank(Berry berry) {
        this.berry = berry;
    }

    @Override
    public boolean validate() {
        return Banking.isInBank() && Inventory.isFull();
    }

    @Override
    public void execute() {
        RSObject booth = Utils.getBankBooths()[0];

        if(Banking.isBankScreenOpen()) {
            RSItem[] totalItemBank = Banking.find(berry.ID);
            if(totalItemBank != null) {
                berry.totalInBank = totalItemBank[0].getStack();
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
