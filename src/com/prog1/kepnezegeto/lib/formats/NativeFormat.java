package com.prog1.kepnezegeto.lib.formats;

import com.prog1.kepnezegeto.App;
import com.prog1.kepnezegeto.lib.IFormat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class NativeFormat implements IFormat {
    protected String[] extensions;

    // Needed for instancing
    public NativeFormat() {}

    public String[] getExtensions() {
        return extensions;
    }

    public BufferedImage loadFile(File file) throws IOException {
        BufferedImage result = null;

        try {
            BufferedImage image = ImageIO.read(file);

            GraphicsConfiguration gc = App.getDefaultConfiguration();
            result = gc.createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.OPAQUE);
            Graphics2D g = result.createGraphics();
            g.drawRenderedImage(image, null);
            g.dispose();
        } catch (Exception e) {
            throw new IOException("Invalid image file!");
        }

        return result;
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
