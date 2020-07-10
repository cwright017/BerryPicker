package scripts.Debug;

import org.tribot.api.General;

public class Debug {
    private static Debug INSTANCE;
    private static DebugLevel level = DebugLevel.NONE;

    private Debug() {
    }

    public static void setLevel(DebugLevel level) {
        General.println("Setting debug level to " + level.toString());
        Debug.level = level;
    }

    public static Debug getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Debug();
        }
        return INSTANCE;
    }

    public void error(String msg) {
        if (level.getValue() <= DebugLevel.ERROR.getValue()) {
            General.println(msg);
        }
    }

    public void info(String msg) {
        if (level.getValue() <= DebugLevel.INFO.getValue()) {
            General.println(msg);
        }
    }

    public void log(String msg) {
        if (level.getValue() <= DebugLevel.VERBOSE.getValue()) {
            General.println(msg);
        }
    }
}
