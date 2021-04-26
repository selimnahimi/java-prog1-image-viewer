package com.prog1.kepnezegeto.lib.operations;

import com.prog1.kepnezegeto.lib.IOperation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public abstract class Operation implements IOperation {
    public void manualExecute(Action action) {
        action.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
    }
}
