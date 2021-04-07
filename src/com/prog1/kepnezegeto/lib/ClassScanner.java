package com.prog1.kepnezegeto.lib;

import com.prog1.kepnezegeto.App;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class ClassScanner {
    public static <T> List<T> scanFolder(Class<T> tClass, String packageName) {
        List<T> list = new ArrayList<T>();

        // Translate the package name into an absolute path
        String name = packageName;
        if (!name.startsWith("/")) {
            name = "/" + name;
        }
        name = name.replace('.','/');

        // Get a File object for the package
        URL url = App.class.getResource(name);
        File directory = new File(url.getFile());

        if (directory.exists()) {
            // Get the list of the files contained in the package
            String [] files = directory.list();
            for (int i=0; i<files.length; i++) {

                // we are only interested in .class files
                if (files[i].endsWith(".class")) {
                    // removes the .class extension
                    String classname = files[i].substring(0,files[i].length()-6);
                    try {
                        // Try to create an instance of the object
                        Class c = Class.forName(packageName+"."+classname);
                        if (tClass.isAssignableFrom(c)) {
                            Object obj = c.newInstance();
                            list.add((T) obj);
                        }
                    } catch (ClassNotFoundException cnfex) {
                        System.err.println("Class not found, skipping...");
                    } catch (InstantiationException iex) {
                        System.err.println("No default constructor, skipping...");
                        // We try to instantiate an interface
                        // or an object that does not have a
                        // default constructor
                    } catch (IllegalAccessException iaex) {
                        System.err.println("Class is not public, skipping...");
                        // The class is not public
                    }
                }
            }
        }

        return list;
    }
}
