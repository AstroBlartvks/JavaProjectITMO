package org.AstrosLab.files;

import org.AstrosLab.collection.CustomCollection;

public class Writer {
    private final WriteHandler writehandler;

    public Writer(WriteHandler writehandler){
        this.writehandler = writehandler;
    }

    public void writeToEnv(CustomCollection collection) throws Exception {
        String Path = System.getenv(JavaFileStrings.JAVAEND);

        if (Path == null){
            throw new Exception("The path to the file in the environment variable '"+JavaFileStrings.JAVAEND+"' was not found!");
        }

        writehandler.writeFile(Path, collection);
    }
}
