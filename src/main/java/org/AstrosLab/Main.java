package org.AstrosLab;

import org.AstrosLab.collection.CustomCollection;

import org.AstrosLab.files.JSonReader;
import org.AstrosLab.files.Reader;

public class Main {
    public static void main(String[] args) {
        CustomCollection collection = new CustomCollection();

        Reader read = new Reader(new JSonReader());
        try {
            collection = read.readFromEnv("JAVATESTFILE");
        } catch (Exception e) {
            System.out.println("Exception: Programm will be closed!\n" + e);
        }

        System.out.println(collection.getRoutesDescriptions());

    }
}
