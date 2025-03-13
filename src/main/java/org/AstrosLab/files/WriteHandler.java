package org.AstrosLab.files;

import org.AstrosLab.collection.CustomCollection;

public abstract class WriteHandler {
    abstract public void writeFile(String Path, CustomCollection collection) throws Exception;
}
