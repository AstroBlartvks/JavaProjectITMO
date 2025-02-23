package org.AstrosLab;

import org.AstrosLab.collectrion.customCollection;
import org.AstrosLab.files.JSonReader;
import org.AstrosLab.files.Reader;

import org.AstrosLab.model.Coordinates;
import org.AstrosLab.model.Location;
import org.AstrosLab.model.Route;

public class Main {
    public static void main(String[] args) {
        customCollection collect = new customCollection();
        Route r1 = new Route("AstroR", new Coordinates(10.4, 11.2),
                new Location(1L, 3F, 2F, "Astro"),
                new Location(2L, 3F, 1F, "Astro"),
                11.2);

        collect.addElement(r1);
        collect.printALl();

    }
}
