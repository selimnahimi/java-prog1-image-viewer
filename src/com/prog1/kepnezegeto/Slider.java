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
                App app = App.mainApp;

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
