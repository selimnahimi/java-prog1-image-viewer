package com.prog1.kepnezegeto.lib;

import com.prog1.kepnezegeto.App;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class ClassScanner {
    public static <T> List<T> scanFolder(Class<T> tClass, String packageName) {
        List<T> classList = new ArrayList<T>();

        // A package címet átírjuk egy relatív útvonalra
        String name = packageName;
        if (!name.startsWith("/")) {
            name = "/" + name;
        }
        name = name.replace('.','/');

        // A package mappájáról lekérünk egy File objektumot
        URL url = App.class.getResource(name);
        File directory = new File(url.getFile());

        if (directory.exists()) {
            // A package által leírt mappában keresünk fájlokat
            String [] files = directory.list();
            assert files != null;

            for (String file : files) {
                if (file.endsWith(".class")) {
                    // Nincs szükségünk a fájl kiterjesztésre
                    String classname = file.replace(".class", "");
                    try {
                        // Példányosítjuk az osztályt
                        Class c = Class.forName(packageName + "." + classname);
                        if (tClass.isAssignableFrom(c)) {
                            Object obj = c.newInstance();
                            classList.add((T) obj);
                        }
                    } catch (ClassNotFoundException cnfex) {
                        //System.err.println("Az osztály nem található, kihagyás...");
                    } catch (InstantiationException iex) {
                        //System.err.println("Az osztálynak nincs default konstruktora, ezért nem lehet példányosítani. Kihagyás...");
                    } catch (IllegalAccessException iaex) {
                        //System.err.println("Az osztály nem publikus, kihagyás...");
                    }
                }
            }
        }

        return classList;
    }
}
