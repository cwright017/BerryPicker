package scripts;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import scripts.Nodes.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author CWright
 */

@ScriptManifest(authors = "CWright", name = "Main", category = "Test")
public class Main extends Script implements Painting {
    private ArrayList<Node> Nodes = new ArrayList<>();
    private int gameState = Game.getSetting(281);

    private long startTime = 0;

    @Override
    public void run() {
        startTime = Timing.currentTimeMillis();

//        DaxWalker.setCredentials(new DaxCredentialsProvider() {
//            @Override
//            public DaxCredentials getDaxCredentials() {
//                return new DaxCredentials("sub_DPjcfqN4YkIxm8", "PUBLIC-KEY");
//            }
//        });

        Collections.addAll(
            Nodes,
//            new Setup(),
            new LoginUser(),
            new Bank(),
            new PickBerries(),
            new WalkToArea(),
            new HopWorld()
        );

        while(true) {
            loop();

//            if (gameState == 1000) {
//                General.println("FINISHED! LOGGING YOU OUT NOW :)");
//                Login.logout();
//                break;
//            }
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
    public void onPaint(Graphics g)
    {
        g.setColor(Color.GREEN);

        g.drawString("Cadava Picker",20,20);
        g.drawString("Time Ran: " + Timing.msToString(Timing.currentTimeMillis() - startTime),20,40);
        g.drawString("Berries picked: " + PickBerries.berriesPicked ,20,60);
        g.drawString("Berries picked per hour: " + perHour(PickBerries.berriesPicked),20,80);
    }

    private String perHour(int gained) {
        return (((int) ((gained) * 3600000D / (System.currentTimeMillis() - startTime))) + "");
    }
}
