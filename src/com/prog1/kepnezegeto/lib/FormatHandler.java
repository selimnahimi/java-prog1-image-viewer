package com.prog1.kepnezegeto.lib;

public class FormatHandler extends ClassHandler<IFormat> {
    public FormatHandler() {
        super(IFormat.class, "com.prog1.kepnezegeto.lib.formats");
    }

    public IFormat whichFormat(String extension) {
        if (extension.contains(".")) {
            String[] split = extension.split("\\.");
            extension = split[split.length-1];
        }
        for (IFormat format: this.getClassList()) {
            for (String ext: format.getExtensions()) {
                if (ext.equals(extension)) return format;
            }
        }

        return null;
    }
}
