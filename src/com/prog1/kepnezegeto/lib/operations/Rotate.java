package com.prog1.kepnezegeto.lib.operations;

import com.prog1.kepnezegeto.App;
import com.prog1.kepnezegeto.lib.forms.Slider;
import com.prog1.kepnezegeto.lib.interfaces.IOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

/**
 * Forgatás operáció kezelő osztály
 */
public class Rotate extends WindowOperation implements IOperation {
    @Override
    protected void openFrame(Action frameAction) {
        Slider slider = new Slider();
        slider.open(frameAction);
    }

    @Override
    protected Action getFrameAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action finalAction = (Action) this.getValue("finalAction");
                BufferedImage image = (BufferedImage) this.getValue("image");

                int sliderValue = (int) this.getValue("sliderValue");

                // Effect goes here
                image = rotate(image,sliderValue * (Math.PI / 180));

                finalAction.putValue("image", image);
                manualExecute(this, finalAction);
            }
        };
    }

    /**
     * Kép forgatása
     * @param image Kép, amit forgatni szeretnénk
     * @param angle Kép forgatás foka
     * @return Forgatott kép
     */
    private BufferedImage rotate(BufferedImage image, double angle) {
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h * cos + w * sin);
        GraphicsConfiguration gc = App.getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.OPAQUE);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(angle, w / 2, h / 2);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }

    @Override
    public String getName() {
        return "Rotate";
    }
}
