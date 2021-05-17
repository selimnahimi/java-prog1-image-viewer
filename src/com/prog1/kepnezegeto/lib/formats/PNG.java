package com.prog1.kepnezegeto.lib.formats;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PNG extends NativeFormat {
    public PNG() {
        this.extensions = new String[] {"png"};
    }

    @Override
    public BufferedImage loadFile(File file) throws IOException {
        System.out.println("Ez egy PNG fájl");

        return super.loadFile(file);
    }
}
