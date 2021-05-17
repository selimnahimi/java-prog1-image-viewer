package com.prog1.kepnezegeto.lib;

import javax.swing.*;

/**
 * Egy olyan GUI ablak, amely egy operációt segít az input bevitelben.
 */
public interface IOperationFrame {
    /**
     * GUI ablak megnyitása
     *
     * @param frameAction Java Swing Action, ami az ablak befejezésénél le kell fusson.
     */
    public void open(Action frameAction);

    /**
     * GUI ablak bezárása
     */
    public void close();
}
