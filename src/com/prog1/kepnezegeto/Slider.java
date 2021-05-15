package com.prog1.kepnezegeto;

import com.prog1.kepnezegeto.lib.IOperationFrame;
import com.prog1.kepnezegeto.lib.operations.Operation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Dictionary;
import java.util.Hashtable;


public class Slider extends JFrame implements IOperationFrame {
    private JSlider sliderValue;
    private JPanel panelSlider;
    private JButton buttonSelect;

    public void open(Action frameAction) {
        BufferedImage image = (BufferedImage) frameAction.getValue("image");

        setContentPane(panelSlider);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(320,140);

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

        buttonSelect.addActionListener((ActionEvent e) -> {

            frameAction.putValue("sliderValue", sliderValue.getValue());
            Operation.manualExecute(this, frameAction);

            close();
        });
    }

    public void close() {
        setVisible(false);
        dispose();
    }
}
