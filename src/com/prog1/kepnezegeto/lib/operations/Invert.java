package com.prog1.kepnezegeto.lib.operations;

import com.prog1.kepnezegeto.lib.IOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;

/**
 * Invertálás operáció kezelő osztály
 */
public class Invert extends Operation implements IOperation {
    @Override
    public void execute(BufferedImage image, Action action) {

        BufferedImage newImage = invert(image);

        action.putValue("image", newImage);

        manualExecute(this, action);
    }

    /**
     * Kép invertálása
     *
     * @param image Invertálandó kép
     * @return Invertált kép
     */
    public BufferedImage invert(BufferedImage image) {
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

        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.OPAQUE);
        op.filter(image, newImage);

        return newImage;
    }

    @Override
    public String getName() {
        return "Invert";
    }
}
