package com.prog1.kepnezegeto.lib.operations;

import com.prog1.kepnezegeto.lib.interfaces.IOperation;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Egy olyan operációt leíró osztály, mely egy ablakon kezeli az inputot,
 * és az alapján hajta végre a módosításokat a képen
 */
public abstract class WindowOperation extends Operation implements IOperation {
    private Action finalAction;
    private BufferedImage image;

    @Override
    public void execute(BufferedImage image, Action finalAction) {
        Action frameAction = getFrameAction();

        frameAction.putValue("finalAction", finalAction);
        frameAction.putValue("image", image);

        openFrame(frameAction);
    }

    /**
     * JFrame megnyitása
     * @param frameAction Java Swing Action, ami lefut, miután a megnyitott ablak végzett
     */
    protected abstract void openFrame(Action frameAction);

    /**
     * Egy Java Swing Action lekérése, amely megadja, mi történjen, miután a megnyitott ablak végzett.
     * @return Java Swing Action, ami lefut, miután a megnyitott ablak végzett
     */
    protected abstract Action getFrameAction();
}
