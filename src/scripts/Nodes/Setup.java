package scripts.Nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Game;

public class Setup extends Node {
    final int DESIRED_CAMERA_ZOOM = 1941;
    final int DESIRED_CAMERA_ANGLE = 100;

    private boolean shouldAlterZoom() {
        return Game.getCameraZ() != DESIRED_CAMERA_ZOOM;
    }

    private boolean shouldAlterAngle() {
        return Camera.getCameraAngle() != DESIRED_CAMERA_ANGLE;
    }

    @Override
    public boolean validate() {
        return shouldAlterAngle();
    }

    @Override
    public void execute() {
        boolean shouldZoomOut = Game.getCameraZ() < DESIRED_CAMERA_ZOOM;

//        if (shouldAlterZoom()) {
//            Mouse.scroll(shouldZoomOut, 1); //Alter zoom;
//        }

        if (shouldAlterAngle()) {
            Camera.setCameraAngle(DESIRED_CAMERA_ANGLE);

            Timing.waitCondition(() ->  shouldAlterAngle(), General.random(2000, 4000));

        }

        General.println("Zoom " + Game.getCameraZ());
    }
}
