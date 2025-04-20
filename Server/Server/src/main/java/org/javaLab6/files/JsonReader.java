package org.javaLab6.files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaLab6.collection.CustomCollection;
import org.javaLab6.utils.model.Route;

import java.io.File;
import java.util.Set;

/**
 * A class responsible for reading JSON files and converting them into a {@link CustomCollection} of {@link Route} objects.
 */
public class JsonReader extends ReadHandler {

    /**
     * Reads a JSON file and parses its content into a {@link CustomCollection}.
     *
     * @param path the file path of the JSON file to be read.
     * @return a {@link CustomCollection} containing parsed routes.
     * @throws Exception if the file cannot be read or contains invalid data.
     */
    @Override
    public CustomCollection readFile(String path) throws Exception {
        File jsonFile = new File(path);
        CustomCollection customCollection = new CustomCollection();
        ObjectMapper objectMapper = new ObjectMapper();

        Set<JsonNode> routeList = objectMapper.readValue(jsonFile, new TypeReference<>() {});

        int maxId = 0;

        for (JsonNode routeNode : routeList) {
            boolean validCoordinates;
            boolean validLocation;
            boolean hasAtMinimumOneException;
            
            hasAtMinimumOneException = false;
            if (!routeNode.has("name") || routeNode.get("name").asText().trim().isEmpty()) {
                System.err.println("Warning about: \n\tHAS AN EXCEPTION: 'name' can't be null or empty. 'Route' will not be added to the collection");
                hasAtMinimumOneException = true;
            }

            if (!routeNode.has("coordinates") || !routeNode.get("coordinates").has("x") || !routeNode.get("coordinates").has("y") || routeNode.get("coordinates").isNull()) {
                System.err.println("Warning about: \n\tHAS AN EXCEPTION: 'coordinates' can't be null. 'Route' will not be added to the collection");
                hasAtMinimumOneException = true;
            }

            validCoordinates = validateCoordinates(routeNode.get("coordinates"));
            if (!validCoordinates){
                System.err.println("Warning about:\n\tHAS INVALID COORDINATES. 'Route' will not be added to the collection");
                hasAtMinimumOneException = true;
            }

            validateNumericType(routeNode, "distance", Double.class);
            if (routeNode.get("distance").asDouble() <= 1) {
                System.err.println("Warning about: \n\tHAS AN EXCEPTION: 'distance' must be greater than 1. 'Route' will not be added to the collection");
                hasAtMinimumOneException = true;
            }

            if (routeNode.has("from")) {
                if (!routeNode.get("from").isNull()) {
                    validLocation = validateLocation(routeNode.get("from"), "from");
                    if (!validLocation){
                        System.err.println("Warning about:\n\tHAS INVALID LOCATION 'from'. 'Route' will not be added to the collection");
                        hasAtMinimumOneException = true;
                    }
                }
            } else {
                System.err.println("Warning about: \n\tHAS AN EXCEPTION: Missing required 'from' argument. 'Route' will not be added to the collection");
            }

            if (routeNode.has("to")) {
                if (!routeNode.get("to").isNull()) {
                    validLocation = validateLocation(routeNode.get("to"), "to");
                    if (!validLocation){
                        System.err.println("Warning about:\n\tHAS INVALID LOCATION 'to'. 'Route' will not be added to the collection");
                        hasAtMinimumOneException = true;
                    }
                }
            } else {
                System.err.println("Warning about: \n\tHAS AN EXCEPTION: Missing required 'to' argument. 'Route' will not be added to the collection");
                hasAtMinimumOneException = true;
            }

            if (!hasAtMinimumOneException) {
                Route route = objectMapper.treeToValue(routeNode, Route.class);
                customCollection.addElement(route);
                maxId = Math.max(route.getId(), maxId);
            }else{
                System.err.println("These errors are in the 'Route':\n\t"+routeNode+"\n");
            }
        }

        customCollection.setNextId(maxId + 1);
        return customCollection;
    }


    /**
     * Validates the presence and numeric type of coordinates.
     *
     * @param node the JSON node containing coordinate data.
     */
    private boolean validateCoordinates(JsonNode node) {
        boolean valid = validateNumericType(node, "x", Double.class);
        return valid && validateNumericType(node, "y", Double.class);
    }

    /**
     * Validates that a specific field in a JSON node is of the expected numeric type.
     *
     * @param node         the JSON node containing the field.
     * @param field        the field name to validate.
     * @param expectedType the expected numeric type (Double, Long, Float).
     * @throws IllegalArgumentException if the field is missing or not of the expected type.
     */
    private boolean validateNumericType(JsonNode node, String field, Class<?> expectedType) {
        if (!node.has(field)) {
            System.err.println("Route" + ": missing required '" + field + "' argument");
            return false;
        }
        JsonNode valueNode = node.get(field);
        if (expectedType == Double.class && !valueNode.isNumber()) {
            System.err.println("Route" + ": '" + field + "' must be a double (found: " + valueNode + ")");
            return false;
        }
        if (expectedType == Long.class && !valueNode.isIntegralNumber()) {
            System.err.println("Route" + ": '" + field + "' must be a long (found: " + valueNode + ")");
            return false;
        }
        if (expectedType == Float.class && !valueNode.isFloatingPointNumber() && !valueNode.isNumber()) {
            System.err.println("Route" + ": '" + field + "' must be a float (found: " + valueNode + ")");
            return false;
        }
        return true;
    }

    /**
     * Validates that a location node contains valid numeric coordinates and a non-empty name.
     *
     * @param node      the JSON node representing the location.
     * @param fieldName the name of the location field ("from" or "to").
     * @throws IllegalArgumentException if any required field is missing or invalid.
     */
    private boolean validateLocation(JsonNode node, String fieldName) {
        boolean valid = validateNumericType(node, "x", Long.class);
        valid = valid && validateNumericType(node, "y", Float.class);
        valid = valid && validateNumericType(node, "z", Float.class);

        if (!node.has("name") || !node.get("name").isTextual() || node.get("name").asText().trim().isEmpty()) {
            System.err.println("Route" + ": '" + fieldName + ".name' must be a non-empty string");
            return false;
        }
        return valid;
    }
}
