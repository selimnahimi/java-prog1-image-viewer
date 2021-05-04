package com.prog1.kepnezegeto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;

public class RGBSlider extends JFrame{
    private JSlider sliderRed;
    private JSlider sliderGreen;
    private JSlider sliderBlue;
    private JButton buttonOk;
    private JPanel panelRGB;
    private App app;

    public RGBSlider(App app) {
        setContentPane(panelRGB);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(320,300);

        Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
        labelTable.put(0, new JLabel("0"));
        labelTable.put(50, new JLabel("50"));
        labelTable.put(100, new JLabel("100"));

        sliderRed.setLabelTable(labelTable);
        sliderGreen.setLabelTable(labelTable);
        sliderBlue.setLabelTable(labelTable);

        this.app = app;

        buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                app.originalImage = app.createInverted(app.originalImage);
                app.resizedImage = app.createInverted(app.resizedImage);
                app.labelImage.setIcon(new ImageIcon(app.resizedImage));

                setVisible(false);
                dispose();
            }
        });
    }
}
