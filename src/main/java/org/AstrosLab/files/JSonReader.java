package org.AstrosLab.files;

import org.AstrosLab.collectrion.customCollection;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class JSonReader extends ReadHandler {
    @Override
    public customCollection readFile(String Path){
        customCollection newCollection = new customCollection();
        JSONParser parser = new JSONParser();

        try (FileReader filereader = new FileReader(Path)) {
            JSONObject rootJsonObject = (JSONObject) parser.parse(filereader);

            ArrayList<String> RoutesNames = (ArrayList<String>) rootJsonObject.get("RoutesNames");
            System.out.println(RoutesNames);
            // Типо будет парсить дальше и кидать уже потом в кастомную коллекцию

        } catch (FileNotFoundException e){
            System.err.println("Файл по пути " + Path + " не был обнаружен!" );
            return null;
        } catch (IOException | ParseException e) {
            System.err.println("Ошибка в чтении или парсинге файла!");
            return null;
        }

        return null;
    }
}
