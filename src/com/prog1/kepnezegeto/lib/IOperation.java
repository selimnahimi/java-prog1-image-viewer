package com.prog1.kepnezegeto.lib;

import java.awt.image.BufferedImage;

public interface IOperation {
    String getName();
    String getParam();
    String setParam();
    BufferedImage execute(BufferedImage image);
}
