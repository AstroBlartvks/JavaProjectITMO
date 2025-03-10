package org.AstrosLab.files;
import org.AstrosLab.collection.CustomCollection;


public class Reader {
    private final ReadHandler readhandler;

    public Reader(ReadHandler readhandler){
        this.readhandler = readhandler;
    }

    public CustomCollection readFromEnv(String envName) throws Exception {
        String Path = System.getenv(envName);

        if (Path == null){
            throw new Exception("The path to the file in the environment variable '"+envName+"' was not found!");
        }

        return readhandler.readFile(Path);
    }
}
