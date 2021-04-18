package com.prog1.kepnezegeto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Slider extends JFrame{
    private JSlider sliderValue;
    private JPanel panelSlider;
    private JButton buttonSelect;

    public Slider(){
        setContentPane(panelSlider);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(300,100);

        buttonSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                double value = sliderValue.getValue();
                App.rotationAngle = value;
                setVisible(false);
                dispose();
            }
        });
    }


}
