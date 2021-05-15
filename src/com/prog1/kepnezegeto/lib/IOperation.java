package com.prog1.kepnezegeto.lib;

import javax.swing.*;
import java.awt.image.BufferedImage;

public interface IOperation {
    String getName();
    void execute(BufferedImage image, Action toDo);
}
