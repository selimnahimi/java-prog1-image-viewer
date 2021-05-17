package com.prog1.kepnezegeto.lib;

/**
 * IFormat interfészt használó osztálykezelő osztály
 */
public class FormatHandler extends ClassHandler<IFormat> {
    public FormatHandler() {
        super(IFormat.class, "com.prog1.kepnezegeto.lib.formats");
    }

    /**
     * Egy bejövő fájl útvonalból kideríti, hogy az adott kiterjesztés melyik tárolt osztályhoz tartozik.
     * @param extension Kiterjesztés
     * @return Formátum interfészt megvalósító osztály, amely a kiterjesztéshez tartozik
     */
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
