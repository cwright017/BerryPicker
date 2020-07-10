package scripts.Nodes;

import scripts.Debug.Debug;

public abstract class Node {
    private Debug debug = Debug.getInstance();

    public void printStatus() {
        debug.log("Executing " + this.getClass().getName());
    };
    public abstract boolean validate();
    public abstract void execute();
}
