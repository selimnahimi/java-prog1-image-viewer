package com.prog1.kepnezegeto.lib.operations;

import com.prog1.kepnezegeto.lib.IOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Vízszintes tükrözés operáció kezelő osztály
 */
public class FlipHor extends Operation implements IOperation {
    @Override
    public void execute(BufferedImage image, Action action) {

        BufferedImage newImage = flip(image);

        action.putValue("image", newImage);

        manualExecute(this, action);
    }

    /**
     * Kép tükrözése vízszintesen
     * @param image kép, amit tükrözünk
     * @return tükrözött kép
     */
    public static BufferedImage flip(BufferedImage image) {
        BufferedImage flipped = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        AffineTransform tran = AffineTransform.getTranslateInstance(image.getWidth(), 0);
        AffineTransform flip = AffineTransform.getScaleInstance(-1d, 1d);
        tran.concatenate(flip);

        Graphics2D g = flipped.createGraphics();
        g.setTransform(tran);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return flipped;
    }

    @Override
    public String getName() {
        return "Flip Horizontal";
    }
}
