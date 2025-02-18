package org.AstrosLab;
import org.AstrosLab.model.*;
import org.AstrosLab.files.Reader;
import org.AstrosLab.files.JSonReader;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        Reader reader = new Reader(new JSonReader());
        Route r1 = reader.readFromEnv();

    }
}
