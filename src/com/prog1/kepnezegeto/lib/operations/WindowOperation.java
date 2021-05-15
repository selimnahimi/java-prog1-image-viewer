package com.prog1.kepnezegeto.lib.operations;

import com.prog1.kepnezegeto.lib.IOperation;

import javax.swing.*;
import java.awt.image.BufferedImage;

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

    protected abstract void openFrame(Action frameAction);

    protected abstract Action getFrameAction();
}
