package org.AstrosLab.files;
import org.AstrosLab.collectrion.customCollection;
import org.AstrosLab.validate.ValidateRoute;


public class Reader {
    private final ReadHandler readhandler;

    public Reader(ReadHandler readhandler){
        this.readhandler = readhandler;
    }

    public Exception getException(){
        return this.readhandler.getException();
    }

    public customCollection readFromEnv() {
        String Path = System.getenv("JAVATESTFILE");

        if (Path == null){
            System.err.println("Путь к файлу в переменной окружения JAVATESTFILE не найден!");
            return null;
        }

        return readhandler.readFile(Path);
    }
}
