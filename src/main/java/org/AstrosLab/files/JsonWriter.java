package org.AstrosLab.files;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.AstrosLab.collection.CustomCollection;
import org.AstrosLab.model.Route;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class JsonWriter extends WriteHandler {
    @Override
    public void writeFile(String Path, CustomCollection collection) throws Exception {
        HashMap<String, Route> NameObjectRoutes = new HashMap<String, Route>();
        for (Route r : collection.getCollection()){
            String nameObject = "Route_" + r.getId();
            NameObjectRoutes.put(nameObject, r);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(Path), NameObjectRoutes);
        } catch (IOException e){
            throw new Exception("Check the file: '" + Path + "'. it may be corrupted or may not exist");
        }
    }
}
