package org.AstrosLab.files;

import org.AstrosLab.collectrion.customCollection;

public abstract class ReadHandler {
    protected Exception error = null;

    abstract public customCollection readFile(String Path);
    abstract public Exception getException();
}
