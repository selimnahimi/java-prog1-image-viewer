package com.prog1.kepnezegeto.lib.operations;

import com.prog1.kepnezegeto.lib.IOperation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

/**
 * Operációt leíró osztály
 */
public abstract class Operation implements IOperation {
    /**
     * Egy Swing Action manuális meghívása
     * @param source Meghívó objektum
     * @param action Futtatandó akció
     */
    public static void manualExecute(Object source, Action action) {
        action.actionPerformed(new ActionEvent(source, ActionEvent.ACTION_PERFORMED, null));
    }
}
