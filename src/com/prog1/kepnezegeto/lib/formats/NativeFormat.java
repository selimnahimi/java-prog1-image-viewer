package com.prog1.kepnezegeto.lib.formats;

import com.prog1.kepnezegeto.lib.IFormat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class NativeFormat implements IFormat {
    protected String[] extensions;

    // Needed for instancing
    public NativeFormat() {}

    public String[] getExtensions() {
        return extensions;
    }

    public BufferedImage loadFile(File file) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(file);
        } catch(Exception e) {
            // TODO: EXCEPTION
        }

        return image;
    }

    public boolean exportFile(BufferedImage image, String path) {
        File output = new File(path);

        try {
            ImageIO.write(image, getExtensions()[0], output);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
