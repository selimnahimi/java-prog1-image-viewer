package com.prog1.kepnezegeto.lib.forms;

import com.prog1.kepnezegeto.lib.IOperationFrame;
import com.prog1.kepnezegeto.lib.operations.Operation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * RGB Slider JFrame
 */
public class RGBSlider extends JFrame implements IOperationFrame {
    private JSlider sliderRed;
    private JSlider sliderGreen;
    private JSlider sliderBlue;
    private JButton buttonOk;
    private JPanel panelRGB;
    private JLabel labelRed;
    private JLabel labelGreen;
    private JLabel labelBlue;

    public void open(Action frameAction) {
        setContentPane(panelRGB);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(320, 300);

        Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
        labelTable.put(-255, new JLabel("-255"));
        labelTable.put(0, new JLabel("0"));
        labelTable.put(255, new JLabel("255"));

        sliderRed.setLabelTable(labelTable);
        sliderGreen.setLabelTable(labelTable);
        sliderBlue.setLabelTable(labelTable);

        buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frameAction.putValue("sliderValueRed", sliderRed.getValue());
                frameAction.putValue("sliderValueGreen", sliderGreen.getValue());
                frameAction.putValue("sliderValueBlue", sliderBlue.getValue());
                Operation.manualExecute(this, frameAction);

                close();
            }
        });
    }

    public void close() {
        setVisible(false);
        dispose();
    }
}
