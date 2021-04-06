package com.prog1.kepnezegeto.lib;

import java.awt.image.BufferedImage;
import java.io.File;

public interface IFormat {
    String[] getExtensions();
    BufferedImage loadFile(File file);
    boolean exportFile(BufferedImage image, String path);
}
