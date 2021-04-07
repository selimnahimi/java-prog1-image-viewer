package com.prog1.kepnezegeto.lib;

import com.prog1.kepnezegeto.App;
import com.prog1.kepnezegeto.lib.formats.NativeFormat;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FormatHandler {
    private static String packageName = "com.prog1.kepnezegeto.lib.formats";
    private List<IFormat> formats;

    public List<IFormat> getFormats() {
        return formats;
    }

    public void reloadFormats() {
        this.formats = loadFormats();
    }

    public FormatHandler() {
        reloadFormats();
    }

    private List<IFormat> loadFormats() {
        return ClassScanner.scanFolder(IFormat.class, this.packageName);
    }
}
