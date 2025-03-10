package org.AstrosLab;

import org.AstrosLab.collection.CustomCollection;

import org.AstrosLab.files.JsonReader;
import org.AstrosLab.files.Reader;
import org.AstrosLab.inputManager.InputManager;


public class Main {
    public static void main(String[] args) {
        CustomCollection collection = new CustomCollection();
        InputManager inputManager = new InputManager();

        Reader read = new Reader(new JsonReader());
        try {
            collection = read.readFromEnv("JAVATESTFILE");
        } catch (Exception e) {
            System.out.println("Exception: Programm will be closed!\n" + e);
        }

        while (inputManager.hasNextInput()){
            inputManager.input();
        }
    }
}
