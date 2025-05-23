package AstroLabServer.files;

import AstroLab.utils.model.Route;
import AstroLabServer.collection.CustomCollection;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A class responsible for reading JSON files and converting
 * them into a {@link CustomCollection} of {@link Route} objects.
 */
public class JsonReader extends ReadHandler {
    private static final Logger LOGGER = LogManager.getLogger(JsonReader.class);

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
            boolean hasAtMinimumOneException;
            
            hasAtMinimumOneException = false;
            if (!routeNode.has("name") || routeNode.get("name").asText().trim().isEmpty()) {
                LOGGER.warn("Warning about: \n\tHAS AN EXCEPTION: 'name' can't be null or empty. " +
                        "'Route' will not be added to the collection");
                hasAtMinimumOneException = true;
            }

            if (!routeNode.has("coordinates") ||
                    !routeNode.get("coordinates").has("x") ||
                    !routeNode.get("coordinates").has("y") ||
                    routeNode.get("coordinates").isNull()) {
                LOGGER.warn("Warning about: \n\tHAS AN EXCEPTION: 'coordinates' " +
                        "can't be null. 'Route' will not be added to the collection");
                hasAtMinimumOneException = true;
            }

            boolean validCoordinates = validateCoordinates(routeNode.get("coordinates"));
            if (!validCoordinates) {
                LOGGER.warn("Warning about:\n\tHAS INVALID COORDINATES. " +
                        "'Route' will not be added to the collection");
                hasAtMinimumOneException = true;
            }

            validateNumericType(routeNode, "distance", Double.class);
            if (routeNode.get("distance").asDouble() <= 1) {
                LOGGER.warn("Warning about: \n\tHAS AN EXCEPTION: 'distance' must be greater than 1. " +
                        "'Route' will not be added to the collection");
                hasAtMinimumOneException = true;
            }

            if (routeNode.has("from")) {
                if (!routeNode.get("from").isNull()) {
                    boolean validLocation = validateLocation(routeNode.get("from"), "from");
                    if (!validLocation) {
                        LOGGER.warn("Warning about:\n\tHAS INVALID LOCATION 'from'. " +
                                "'Route' will not be added to the collection");
                        hasAtMinimumOneException = true;
                    }
                }
            } else {
                LOGGER.warn("Warning about: \n\tHAS AN EXCEPTION: Missing required 'from' argument. " +
                        "'Route' will not be added to the collection");
            }

            if (routeNode.has("to")) {
                if (!routeNode.get("to").isNull()) {
                    boolean validLocation = validateLocation(routeNode.get("to"), "to");
                    if (!validLocation) {
                        LOGGER.warn("Warning about:\n\tHAS INVALID LOCATION 'to'. " +
                                "'Route' will not be added to the collection");
                        hasAtMinimumOneException = true;
                    }
                }
            } else {
                LOGGER.warn("Warning about: \n\tHAS AN EXCEPTION: Missing required 'to' argument." +
                        " 'Route' will not be added to the collection");
                hasAtMinimumOneException = true;
            }

            if (!hasAtMinimumOneException) {
                Route route = objectMapper.treeToValue(routeNode, Route.class);
                customCollection.addElement(route);
                maxId = Math.max(route.getId(), maxId);
            } else {
                LOGGER.error("These errors are in the 'Route':\n\t{}\n", routeNode);
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
            LOGGER.error("Route: missing required '{}' argument", field);
            return false;
        }
        JsonNode valueNode = node.get(field);
        if (expectedType == Double.class && !valueNode.isNumber()) {
            LOGGER.error("Route: '{}' must be a double (found: {})", field, valueNode);
            return false;
        }
        if (expectedType == Long.class && !valueNode.isIntegralNumber()) {
            LOGGER.error("Route: '{}' must be a long (found: {})", field, valueNode);
            return false;
        }
        if (expectedType == Float.class && !valueNode.isFloatingPointNumber() && !valueNode.isNumber()) {
            LOGGER.error("Route: '{}' must be a float (found: {})", field, valueNode);
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

        if (!node.has("name") ||
                !node.get("name").isTextual() ||
                node.get("name").asText().trim().isEmpty()) {
            LOGGER.error("Route: '{}.name' must be a non-empty string", fieldName);
            return false;
        }
        return valid;
    }
}
