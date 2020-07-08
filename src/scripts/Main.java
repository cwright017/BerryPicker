package scripts;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Arguments;
import org.tribot.script.interfaces.Painting;
import scripts.Nodes.*;
import scripts.Utils.Constants;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.api_lib.models.DaxCredentials;
import scripts.dax_api.api_lib.models.DaxCredentialsProvider;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author CWright
 */

@ScriptManifest(authors = "CWright", name = "Cadava Picker", category = "Gathering")
public class Main extends Script implements Painting, Arguments {
    private ArrayList<Node> Nodes = new ArrayList<>();
    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    private long startTime = 0;

    private double chatX = 0;
    private double chatY = 0;
    private double chatWidth = 0;
    private double chatHeight = 0;

    private Berry berry;

    @Override
    public void run() {
        if(berry == null) {
            return;
        }

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

        chatX = chat.getAbsoluteBounds().getX();
        chatY = chat.getAbsoluteBounds().getY();
        chatWidth = chat.getAbsoluteBounds().getWidth();
        chatHeight = chat.getAbsoluteBounds().getHeight();

        int paintY = (int) chatY;
        int paintX = (int) chatX;
        int paintYGap = 25;

        // Draw banked items
        g.setColor(new Color(0,0, 0, 60));
        g.fillRect(paintX, 45, 100, 35);
        g.drawImage(getImage(berry.SRC), paintX + 10, 50, 25, 25, null);

        g.setColor(Color.WHITE);
        g.drawString("" + (berry.totalInBank + berry.totalInInv), paintX + 45, 70 );

        // Draw chat BG
        g.setColor(Constants.PAINT_BG_COLOR);
        g.fillRect(paintX, paintY, (int) chatWidth, (int) chatHeight);

        // Draw paint
        g.drawImage(getImage(berry.SRC), paintX + (int) chatWidth - 110, paintY + 5, 100, 100, null);
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

    public BufferedImage getImage(String name) {
        try {
            General.println("Loading image: " + name);
            return ImageIO.read(new URL(name));
        } catch (IOException e) {
            General.println(name + " not loaded. ");
        }
        return null;
    }

    @Override
    public void passArguments(HashMap<String, String> arguments) {
        String type = arguments.get("custom_input");

        General.println("ARG: " + type + Constants.Berries.valueOf(type));
        berry = new Berry(Constants.Berries.valueOf(type));
    }
}
