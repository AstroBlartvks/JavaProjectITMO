package org.AstrosLab;

import org.AstrosLab.collectrion.customCollection;
import org.AstrosLab.files.JSonReader;
import org.AstrosLab.files.Reader;

public class Main {
    public static void main(String[] args) {
        customCollection collect = new customCollection();

        Reader read = new Reader(new JSonReader());
        collect = read.readFromEnv();
        if (read.getException() != null){
            System.err.println("Errors were found in the file, fix them: \n" + read.getException());
            return;
        }
        collect.printAll();

    }
}
