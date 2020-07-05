package scripts.Nodes;

import org.tribot.api.General;

public abstract class Node {
    public void printStatus() {
        General.println("Executing " + this.getClass().getName());
    };
    public abstract boolean validate();
    public abstract void execute();
}
