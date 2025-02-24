package org.AstrosLab.files;

import org.AstrosLab.collectrion.customCollection;
import org.AstrosLab.validate.ValidateRoute;

public abstract class ReadHandler {
    protected Exception error = null;
    private final ValidateRoute valdRoute = new ValidateRoute();

    abstract public customCollection readFile(String Path);
    abstract public Exception getException();
}
