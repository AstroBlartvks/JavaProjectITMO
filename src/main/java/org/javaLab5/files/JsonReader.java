package org.javaLab5.files;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaLab5.collection.CustomCollection;
import org.javaLab5.model.Route;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

/**
 * A class responsible for reading JSON files and converting them into a {@link CustomCollection} of {@link Route} objects.
 */
public class JsonReader extends ReadHandler {

    /**
     * Reads a JSON file and parses its content into a {@link CustomCollection}.
     *
     * @param Path the file path of the JSON file to be read.
     * @return a {@link CustomCollection} containing parsed routes.
     * @throws Exception if the file cannot be read or contains invalid data.
     */
    @Override
    public CustomCollection readFile(String Path) throws Exception{
        File jsonFile = new File(Path);
        CustomCollection customCollection = new CustomCollection();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(jsonFile);
        Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String routeKey = entry.getKey();
            JsonNode routeNode = entry.getValue();

            if (!routeNode.has("name") || routeNode.get("name").asText().trim().isEmpty()) {
                throw new IllegalArgumentException(routeKey + ": 'name' can't be null or empty");
            }

            if (!routeNode.has("coordinates") || !routeNode.get("coordinates").has("x") || !routeNode.get("coordinates").has("y") || routeNode.get("coordinates").isNull()) {
                throw new IllegalArgumentException(routeKey + ": 'coordinates' can't be null");
            }

            validateCoordination(routeNode.get("coordinates"), routeKey);

            validateNumericType(routeNode, "distance", Double.class, routeKey);
            if (routeNode.get("distance").asDouble() <= 1) {
                throw new IllegalArgumentException(routeKey + ": 'distance' must be greater than 1");
            }

            if (routeNode.has("from")) {
                if (!routeNode.get("from").isNull()) {
                    validateLocation(routeNode.get("from"), routeKey, "from");
                }
            } else {
                throw new IllegalArgumentException(routeKey + ": missing required 'from' argument");
            }

            if (routeNode.has("to")) {
                if (!routeNode.get("to").isNull()) {
                    validateLocation(routeNode.get("to"), routeKey, "to");
                }
            } else {
                throw new IllegalArgumentException(routeKey + ": missing required 'to' argument");
            }
        }

        Map<String, Route> routes = objectMapper.readValue(jsonFile, objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Route.class));
        for (String routeName: routes.keySet()) {
            customCollection.addElement(routes.get(routeName));
        }

        return customCollection;
    }

    /**
     * Validates the presence and numeric type of coordinates.
     *
     * @param node     the JSON node containing coordinate data.
     * @param routeKey the key of the route being validated.
     */
    private static void validateCoordination(JsonNode node, String routeKey) {
        validateNumericType(node, "x", Double.class, routeKey);
        validateNumericType(node, "y", Double.class, routeKey);
    }

    /**
     * Validates that a specific field in a JSON node is of the expected numeric type.
     *
     * @param node         the JSON node containing the field.
     * @param field        the field name to validate.
     * @param expectedType the expected numeric type (Double, Long, Float).
     * @param routeKey     the key of the route being validated.
     * @throws IllegalArgumentException if the field is missing or not of the expected type.
     */
    private static void validateNumericType(JsonNode node, String field, Class<?> expectedType, String routeKey) {
        if (!node.has(field)) {
            throw new IllegalArgumentException(routeKey + ": missing required '" + field + "' argument");
        }
        JsonNode valueNode = node.get(field);
        if (expectedType == Double.class && !valueNode.isNumber()) {
            throw new IllegalArgumentException(routeKey + ": '" + field + "' must be a double (found: " + valueNode + ")");
        }
        if (expectedType == Long.class && !valueNode.isIntegralNumber()) {
            throw new IllegalArgumentException(routeKey + ": '" + field + "' must be a long (found: " + valueNode + ")");
        }
        if (expectedType == Float.class && !valueNode.isFloatingPointNumber() && !valueNode.isNumber()) {
            throw new IllegalArgumentException(routeKey + ": '" + field + "' must be a float (found: " + valueNode + ")");
        }
    }

    /**
     * Validates that a location node contains valid numeric coordinates and a non-empty name.
     *
     * @param node      the JSON node representing the location.
     * @param routeKey  the key of the route being validated.
     * @param fieldName the name of the location field ("from" or "to").
     * @throws IllegalArgumentException if any required field is missing or invalid.
     */
    private static void validateLocation(JsonNode node, String routeKey, String fieldName) {
        validateNumericType(node, "x", Long.class, routeKey);
        validateNumericType(node, "y", Float.class, routeKey);
        validateNumericType(node, "z", Float.class, routeKey);

        if (!node.has("name") || !node.get("name").isTextual() || node.get("name").asText().trim().isEmpty()) {
            throw new IllegalArgumentException(routeKey + ": '" + fieldName + ".name' must be a non-empty string");
        }
    }
}
