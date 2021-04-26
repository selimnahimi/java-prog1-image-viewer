package com.prog1.kepnezegeto.lib.operations;

import com.prog1.kepnezegeto.Slider;
import com.prog1.kepnezegeto.lib.IOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;

public class Invert extends Operation implements IOperation {
    @Override
    public void execute(BufferedImage image, Action action) {

        LookupTable lookup = new LookupTable(0, 3) {
            @Override
            public int[] lookupPixel(int[] src, int[] dest) {
                dest[0] = (int) (255 - src[0]);
                dest[1] = (int) (255 - src[1]);
                dest[2] = (int) (255 - src[2]);
                return dest;
            }
        };
        LookupOp op = new LookupOp(lookup, new RenderingHints(null));

        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        op.filter(image, newImage);

        action.putValue("image", newImage);

        manualExecute(action);
    }

    @Override
    public String getName() {
        return "Invert";
    }
}
