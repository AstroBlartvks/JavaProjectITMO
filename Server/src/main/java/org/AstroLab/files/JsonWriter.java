package org.AstroLab.files;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.AstroLab.collection.CustomCollection;
import org.AstroLab.utils.model.Route;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;


/**
 * The {@code JsonWriter} class extends the {@code WriteHandler} class
 * and provides functionality for writing a collection of {@link Route} objects
 * to a JSON file. The class uses Jackson's {@link ObjectMapper} to
 * serialize a {@link CustomCollection} of {@link Route} objects into a JSON
 * format and write them to a specified file path.
 * <p>
 * This class generates a {@link HashMap} where the key is a unique identifier
 * for each route object, and the value is the {@link Route} object itself.
 * The routes are serialized and written to the file in a formatted JSON
 * structure.
 * </p>
 *
 * @see WriteHandler
 * @see CustomCollection
 * @see Route
 */
public class JsonWriter extends WriteHandler {

    /**
     * Serializes a collection of {@link Route} objects and writes them to
     * a specified file path in JSON format.
     *
     * @param Path the path of the file where the JSON data will be written.
     * @param collection the {@link CustomCollection} containing the {@link Route} objects to be serialized.
     * @throws Exception if an I/O error occurs while writing to the file or
     *         if the file is corrupted or does not exist.
     */
    @Override
    public void writeFile(String Path, CustomCollection collection) throws Exception {
        Set<Route> objectRoutes = new TreeSet<>(collection.getCollection());

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(Path), objectRoutes);
        } catch (IOException e) {
            throw new IOException("Check the file: '" + Path + "'. It may be corrupted or may not exist", e);
        }
    }
}
