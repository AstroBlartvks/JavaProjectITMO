package org.AstrosLab.files;
import org.AstrosLab.model.Route;

import java.io.FileNotFoundException;

public class Reader {
    private ReadHandler readhandler;

    public Reader(ReadHandler readhandler){
        this.readhandler = readhandler;
    }

    public Route readFromEnv() {
        String Path = System.getenv("JAVATESTFILE");

        //Windows moment
        Path = "C:\\Users\\AstroBlartvks\\IdeaProjects\\JavaProjectITMO\\src\\test\\collection.json";

        if (Path == null){
            System.err.println("Путь к файлу в переменной окружения JAVATESTFILE не найден!");
            return null;
        }

        return readhandler.readFile(Path);
    }
}
