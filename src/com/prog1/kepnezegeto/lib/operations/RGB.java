package com.prog1.kepnezegeto.lib.operations;

import com.prog1.kepnezegeto.RGBSlider;
import com.prog1.kepnezegeto.lib.IOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;

public class RGB extends WindowOperation implements IOperation {
    @Override
    protected void openFrame(Action frameAction) {
        RGBSlider slider = new RGBSlider();
        slider.open(frameAction);
    }

    @Override
    protected Action getFrameAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action finalAction = (Action) this.getValue("finalAction");
                BufferedImage image = (BufferedImage) this.getValue("image");

                int sliderValueRed = (int) this.getValue("sliderValueRed");
                int sliderValueGreen = (int) this.getValue("sliderValueGreen");
                int sliderValueBlue = (int) this.getValue("sliderValueBlue");

                // Effect goes here
                image = colorEdit(image, sliderValueRed, sliderValueGreen, sliderValueBlue);

                finalAction.putValue("image", image);
                manualExecute(this, finalAction);
            }
        };
    }

    public static BufferedImage colorEdit(BufferedImage image, int r, int g, int b) {
        LookupTable lookup = new LookupTable(0, 3) {
            @Override
            public int[] lookupPixel(int[] src, int[] dest) {
                if (src[0] + r < 255 && src[0] + r > 0) {
                    dest[0] = (int) (src[0] + r);
                }
                if (src[0] + r > 255) {
                    dest[0] = (int) (255);
                }
                if (src[0] + r < 0) {
                    dest[0] = (int) (0);
                }
                if (src[1] + g < 255 && src[1] + g > 0) {
                    dest[1] = (int) (src[1] + g);
                }
                if (src[1] + g > 255) {
                    dest[1] = (int) (255);
                }
                if (src[1] + g < 0) {
                    dest[1] = (int) (0);
                }
                if (src[2] + b < 255 && src[2] + b > 0) {
                    dest[2] = (int) (src[2] + b);
                }
                if (src[2] + b > 255) {
                    dest[2] = (int) (255);
                }
                if (src[2] + b < 0) {
                    dest[2] = (int) (0);
                }
                return dest;
            }
        };
        LookupOp op = new LookupOp(lookup, new RenderingHints(null));
        return op.filter(image, null);
    }

    @Override
    public String getName() {
        return "RGB Edit";
    }
}
