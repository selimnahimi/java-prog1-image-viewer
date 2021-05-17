package com.prog1.kepnezegeto.lib;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface IFormat {
    String[] getExtensions();
    BufferedImage loadFile(File file) throws IOException;
    boolean exportFile(BufferedImage image, String path);
}
