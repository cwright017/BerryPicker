package scripts.Debug;

public enum DebugLevel {
    VERBOSE(0),
    ERROR(1),
    INFO(2),
    NONE(99);

    private final int val;

    DebugLevel(int i) {
        this.val = i;
    }

    public int getValue() {
        return this.val;
    }
}
