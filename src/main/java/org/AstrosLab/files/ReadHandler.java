package org.AstrosLab.files;
import org.AstrosLab.model.Route;

import java.io.FileNotFoundException;

public abstract class ReadHandler {

    abstract public Route readFile(String Path);

}
