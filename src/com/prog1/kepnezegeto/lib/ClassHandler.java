package com.prog1.kepnezegeto.lib;

import java.util.ArrayList;
import java.util.List;

/**
 * Egy külső osztályokat kezelő generikus osztály
 * @param <T> Kezelendő osztályok típusa
 */
public class ClassHandler<T> {
    private String packageName = "com.prog1.kepnezegeto.lib.formats";
    private List<T> classes;
    private Class<T> tClass;

    public ClassHandler(Class<T> tClass, String packageName) {
        this.classes = new ArrayList<T>();
        this.packageName = packageName;
        this.tClass = tClass;

        this.reload();
    }

    /**
     * Kezelt osztályok listájának lekérése
     * @return Kezelt osztályok listája
     */
    public List<T> getClassList() {
        return this.classes;
    }

    /**
     * Reflection újrafuttatása, a kezelt osztályok listájának frissítése
     */
    public void reload() {
        this.classes = scan();
    }

    /**
     * Reflection futtatása
     * @return Osztály lista
     */
    public List<T> scan() {
        return ClassScanner.scanFolder(this.tClass, this.packageName);
    }
}
