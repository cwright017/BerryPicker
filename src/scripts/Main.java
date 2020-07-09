package scripts;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Arguments;
import org.tribot.script.interfaces.MouseActions;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;
import scripts.GUI.GUI;
import scripts.Nodes.*;
import scripts.Utils.Constants;
import scripts.Utils.Utils;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.api_lib.models.DaxCredentials;
import scripts.dax_api.api_lib.models.DaxCredentialsProvider;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author CWright
 */

@ScriptManifest(authors = "CWright", name = "Berry Picker", category = "Gathering")
public class Main extends Script implements Painting, Arguments, Starting, MouseActions {
    private ArrayList<Node> Nodes = new ArrayList<>();
    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    private long startTime = 0;

    private Rectangle chatRect;

    private boolean showPaint = true;

    private BufferedImage image;
    private Berry berry;

    @Override
    public void onStart() {
        if(berry != null) {
            return; // Arg set so don't show GUI
        }

        GUI gui = new GUI();
        gui.setVisible(true);

        while(gui.isShowing()) {
            General.sleep(100);
        }

        General.println("GUI Closed");

        berry = new Berry(gui.selectedBerry);

        gui.dispose();
    }

    @Override
    public void run() {
        General.println("Berry selected: " + berry.NAME);

        if(berry == null) {
            General.println("Invalid berry selected");
            return;
        }

        image = Utils.getImage(berry.SRC);

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
            new Bank(berry),
            new PickBerries(berry),
            new WalkToArea(berry),
            new HopWorld(berry)
        );

        while(true) {
            loop();
        }
    }

    private void loop() {
        for (final Node node: Nodes) {
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
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHints(aa);

        Font font = new Font("Verdana", Font.BOLD, 12);

        RSInterface chat = Interfaces.get(162, 59);
        if (chat == null) {
            return;
        }

        chatRect = new Rectangle(chat.getAbsoluteBounds());

        int paintY = (int) chatRect.getY();
        int paintX = (int) chatRect.getX();
        int paintYGap = 25;

        // Draw banked items
        g.setColor(new Color(0,0, 0, 60));
        g.fillRect(paintX, 45, 100, 35);
        g.drawImage(image, paintX + 10, 50, 25, 25, null);

        g.setColor(Color.WHITE);
        g.drawString("" + (berry.totalInBank + berry.totalInInv), paintX + 45, 70 );

        if (!showPaint) {
            return;
        }

        // Draw chat BG
        g.setColor(Constants.PAINT_BG_COLOR);
        g.fill(chatRect);

        // Draw paint
        g.drawImage(image, paintX + (int) chatRect.getWidth() - 110, paintY + 5, 100, 100, null);
        g.setColor(Constants.PAINT_COLOR);
        g.setFont(font);

        g.drawString("Berry Picker", paintX, paintY += paintYGap);
        g.drawString("Runtime: " + Timing.msToString(Timing.currentTimeMillis() - startTime), paintX, paintY += paintYGap);
        g.drawString("Berries picked: " + berry.totalCollected , paintX, paintY += paintYGap);
        g.drawString("Berries picked per hour: " + perHour(berry.totalCollected), paintX, paintY += paintYGap);
    }

    private String perHour(int gained) {
        return (((int) ((gained) * 3600000D / (System.currentTimeMillis() - startTime))) + "");
    }

    @Override
    public void passArguments(HashMap<String, String> arguments) {
        String type = arguments.get("custom_input");

        General.println("ARG passed: " + type);

        if(type == "") {
            return;
        }

        Constants.Berries berryType = Constants.Berries.valueOf(type);

        if(berryType == null) {
            return;
        }

        berry = new Berry(berryType);
    }

    @Override
    public void mouseMoved(Point point, boolean b) {

    }

    @Override
    public void mouseDragged(Point point, int i, boolean b) {

    }

    @Override
    public void mouseReleased(Point point, int i, boolean b) {

    }

    @Override
    public void mouseClicked(Point point, int i, boolean b) {
        if (chatRect.contains(point)) {
            showPaint = !showPaint;
        }
    }
}
