package scripts.GUI;

import scripts.Utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;

public class GUI extends JFrame {
    public Constants.Berries selectedBerry;

    public GUI() {
        initComponents();
    }

    private void initComponents() {
        this.setSize(400,200);

        JButton button1,button2;

        try {
//            button1 = new JButton("Redberry");
            button1 = new JButton(new ImageIcon(new URL(Constants.REDBERRY_SRC)));
            button1.setToolTipText("Redberry");
            button1.addActionListener((ActionEvent e) -> {
                selectedBerry = Constants.Berries.REDBERRY;

                this.setVisible(false);
            });

//            button2 = new JButton("Cadava Berry");
            button2 = new JButton(new ImageIcon(new URL(Constants.CADAVA_BERRY_SRC)));
            button2.setToolTipText("Cadava Berry");
            button2.addActionListener((ActionEvent e) -> {
                selectedBerry = Constants.Berries.CADAVA_BERRY;

                this.setVisible(false);
            });

            this.add(button1);
            this.add(button2);

        } catch(MalformedURLException e){}


        this.setLayout((new GridLayout(1,2)));

        this.setResizable(false);
        this.setVisible(true);

    }


}
