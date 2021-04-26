package com.prog1.kepnezegeto;

import com.prog1.kepnezegeto.lib.operations.Operation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Dictionary;
import java.util.Hashtable;


public class Slider extends JFrame{
    private JSlider sliderValue;
    private JPanel panelSlider;
    private JButton buttonSelect;
    private Action action;
    private BufferedImage image;
    private Operation operation;

    public Slider(Operation operation, Action action){
        this.image = (BufferedImage) action.getValue("image");

        setContentPane(panelSlider);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(320,140);

        this.action = action;
        this.operation = operation;

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

            double value = sliderValue.getValue();

            this.image = rotate(this.image,value * (Math.PI / 180));
            this.action.putValue("image", this.image);
            this.operation.manualExecute(this.action);

            setVisible(false);
            dispose();
        });
    }

    public static BufferedImage rotate(BufferedImage image, double angle) {
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h * cos + w * sin);
        GraphicsConfiguration gc = App.getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(angle, w / 2, h / 2);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }
}
