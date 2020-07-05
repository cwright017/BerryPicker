package scripts;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Login;
import org.tribot.api2007.types.RSInterface;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import scripts.Nodes.*;
import scripts.Utils.Constants;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.api_lib.models.DaxCredentials;
import scripts.dax_api.api_lib.models.DaxCredentialsProvider;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author CWright
 */

@ScriptManifest(authors = "CWright", name = "Cadava Picker", category = "Gathering")
public class Main extends Script implements Painting {
    private ArrayList<Node> Nodes = new ArrayList<>();
    private int gameState = Game.getSetting(281);
    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    private boolean hasStarted = false;

    private long startTime = 0;

    @Override
    public void run() {
        startTime = Timing.currentTimeMillis();

        DaxWalker.setCredentials(new DaxCredentialsProvider() {
            @Override
            public DaxCredentials getDaxCredentials() {
                return new DaxCredentials("sub_DPjXXzL5DeSiPf", "PUBLIC-KEY");
            }
        });

        Collections.addAll(
            Nodes,
            new Setup(),
            new LoginUser(),
            new Bank(),
            new PickBerries(),
            new WalkToArea(),
            new HopWorld()
        );

        while(true) {
            loop();
        }
    }

    private void loop() {
        for (final Node node: Nodes) {
            gameState = Game.getSetting(281);
            if (node.validate()) {
                node.printStatus();
                node.execute();
                General.sleep(General.random(2500, 3000));
            }
        }
    }

    @Override
    public void onPaint(Graphics gg)
    {
        if(Login.getLoginState() != Login.STATE.INGAME) {
            return;
        }

        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHints(aa);

        int paintY = 0;
        int paintX = 0;
        int paintYGap = 25;
        Font font = new Font("Verdana", Font.BOLD, 12);

        RSInterface chat = Interfaces.get(162, 59);
        if (chat == null) {
            return;
        }

        paintX += chat.getAbsoluteBounds().getX();
        paintY += chat.getAbsoluteBounds().getY();

        gg.setColor(Constants.PAINT_BG_COLOR);
        gg.fillRect(paintX, paintY, chat.getAbsoluteBounds().width, chat.getAbsoluteBounds().height);

        gg.setColor(Constants.PAINT_COLOR);
        gg.setFont(font);

        gg.drawString("Cadava Picker", paintX, paintY += paintYGap);
        gg.drawString("Time Ran: " + Timing.msToString(Timing.currentTimeMillis() - startTime), paintX, paintY += paintYGap);
        gg.drawString("Berries picked: " + PickBerries.berriesPicked , paintX, paintY += paintYGap);
        gg.drawString("Berries picked per hour: " + perHour(PickBerries.berriesPicked), paintX, paintY += paintYGap);
    }

    private String perHour(int gained) {
        return (((int) ((gained) * 3600000D / (System.currentTimeMillis() - startTime))) + "");
    }
}
