package com.prog1.kepnezegeto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;


public class Slider extends JFrame{
    private JSlider sliderValue;
    private JPanel panelSlider;
    private JButton buttonSelect;
    private App app;

    public Slider(App app){
        setContentPane(panelSlider);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(320,140);

        this.app = app;

        Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
        labelTable.put(0, new JLabel("0"));
        labelTable.put(45, new JLabel("45"));
        labelTable.put(90, new JLabel("90"));
        labelTable.put(135, new JLabel("135"));
        labelTable.put(180, new JLabel("180"));
        labelTable.put(225, new JLabel("225"));
        labelTable.put(270, new JLabel("270"));
        labelTable.put(315, new JLabel("315"));
        labelTable.put(360, new JLabel("360"));

        sliderValue.setLabelTable(labelTable);

        buttonSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                double value = sliderValue.getValue();

                app.rotationAngle = value;
                app.originalImage = App.rotate(app.originalImage,app.rotationAngle);
                app.resizedImage = App.rotate(app.resizedImage,app.rotationAngle);
                app.labelImage.setIcon(new ImageIcon(app.resizedImage));

                setVisible(false);
                dispose();
            }
        });
    }


}
