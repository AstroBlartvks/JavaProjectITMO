package org.AstrosLab.files;

import org.AstrosLab.collectrion.customCollection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class JSonReader extends ReadHandler {
    @Override
    public customCollection readFile(String Path){
        customCollection newCollection = new customCollection();
        JSONParser parser = new JSONParser();

        try (FileReader filereader = new FileReader(Path)) {
            JSONObject rootJsonObject;

            try {
                rootJsonObject = (JSONObject) parser.parse(filereader);
            } catch (IOException | ParseException e) {
                this.error = e;
                return null;
            }

            Object obj = rootJsonObject.get("RoutesNames");
            ArrayList<String> RoutesNames = new ArrayList<>();

            if (obj instanceof JSONArray jsonArray) {

                for (Object item : jsonArray) {
                    if (item instanceof String) {
                        RoutesNames.add((String) item);
                    } else {
                        this.error = new TitleElementIsNotStringException("The array element is not a string");
                        return null;
                    }
                }
            } else {
                this.error = new RouteNamesIncorrectFormatException("RoutesNames match or contain an incorrect format");
                return null;
            }

            System.out.println(RoutesNames);
            // Типо будет парсить дальше и кидать уже потом в кастомную коллекцию

        } catch (IOException e){
            this.error = e;
            return null;
        }

        return null;
    }

    @Override
    public Exception getException(){
        return this.error;
    }
}
