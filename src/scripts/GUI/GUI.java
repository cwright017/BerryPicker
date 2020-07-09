package scripts.GUI;

import org.tribot.api.General;
import org.tribot.util.Util;
import scripts.Utils.Constants;
import scripts.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GUI extends JFrame {
    public Constants.Berries selectedBerry;

    public GUI() {
        initComponents();
    }

    private void initComponents() {
        this.setSize(400,200);

        General.println("INIT GUI");

        JButton button1,button2;

        button1 = new JButton("Redberry");
        button1.addActionListener((ActionEvent e) -> {
            General.println("CLICK: " + e.getActionCommand());
            selectedBerry = Constants.Berries.REDBERRY;

            this.setVisible(false);
        });

        button2 = new JButton("Cadava Berry");
        button2.addActionListener((ActionEvent e) -> {
            General.println("CLICK: " + e.getActionCommand());
            selectedBerry = Constants.Berries.CADAVA_BERRY;

            this.setVisible(false);
        });

        this.add(button1);
        this.add(button2);

        this.setLayout((new GridLayout(1,2)));

        this.setResizable(false);
        this.setVisible(true);

    }


}
