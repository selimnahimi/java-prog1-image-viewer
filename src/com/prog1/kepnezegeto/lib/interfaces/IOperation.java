package com.prog1.kepnezegeto.lib.interfaces;

import javax.swing.*;
import java.awt.image.BufferedImage;

public interface IOperation {
    /**
     * Operáció nevének lekérdezése
     *
     * @return Operáció neve
     */
    String getName();

    /**
     * Operáció futtatása az adott képen
     *
     * @param image Kép
     * @param toDo  Swing Action, amit futtat a végén
     */
    void execute(BufferedImage image, Action toDo);
}
