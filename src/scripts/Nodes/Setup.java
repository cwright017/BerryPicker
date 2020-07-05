package scripts.Nodes;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;

public class Setup extends Node {
    final int DESIRED_CAMERA_ZOOM = 1951;

    @Override
    public boolean validate() {
        return Game.getCameraZ() != DESIRED_CAMERA_ZOOM;
    }

    @Override
    public void execute() {
        General.println("Zoom " + Game.getCameraZ());
        boolean shouldZoomOut = Game.getCameraZ() < DESIRED_CAMERA_ZOOM;

        Mouse.scroll(shouldZoomOut, 1); // Zoom in fully;

        General.println("Zoom " + Game.getCameraZ());
    }
}
