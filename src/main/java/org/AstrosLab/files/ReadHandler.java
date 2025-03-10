package org.AstrosLab.files;

import org.AstrosLab.collection.CustomCollection;

public abstract class ReadHandler {
    abstract public CustomCollection readFile(String Path) throws Exception;
}
