package com.prog1.kepnezegeto.lib.operations;

import com.prog1.kepnezegeto.App;
import com.prog1.kepnezegeto.Slider;
import com.prog1.kepnezegeto.lib.IOperation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class Rotate extends Operation implements IOperation {
    @Override
    public void execute(BufferedImage image, Action action) {
        action.putValue("image", image);

        Slider slider = new Slider(this, action);
    }

    @Override
    public String getName() {
        return "Rotate";
    }
}
