package com.prog1.kepnezegeto.lib;

import java.util.ArrayList;
import java.util.List;

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

    public List<T> getClassList() {
        return this.classes;
    }

    public void reload() {
        this.classes = scan();
    }

    public List<T> scan() {
        return ClassScanner.scanFolder(this.tClass, this.packageName);
    }
}
