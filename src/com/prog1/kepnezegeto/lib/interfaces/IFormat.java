package com.prog1.kepnezegeto.lib.interfaces;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Formátum interfész
 */
public interface IFormat {
    /**
     * Fájl kiterjesztések lekérdezése
     *
     * @return Fájl kiterjesztések tömbje
     */
    String[] getExtensions();

    /**
     * Kép fájl betöltése
     *
     * @param file Fájl
     * @return Java Swing BufferedImage
     * @throws IOException A fájlt nem sikerült megnyitni
     */
    BufferedImage loadFile(File file) throws IOException;

    /**
     * Kép fájl lementése
     *
     * @param image Kép, amit lementünk
     * @param path  Mentési útvonal
     * @return Igaz, ha sikerült lementeni, hamis, ha nem
     */
    boolean exportFile(BufferedImage image, String path);
}
