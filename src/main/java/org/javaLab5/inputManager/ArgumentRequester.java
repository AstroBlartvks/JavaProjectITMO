package org.javaLab5.inputManager;

import org.javaLab5.model.Coordinates;
import org.javaLab5.model.Location;

import java.util.InvalidPropertiesFormatException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The {@code ArgumentRequester} class provides utility methods to request input
 * of different data types (e.g., {@code Double}, {@code Float}, {@code String},
 * {@code Long}) from the user via the console. Each method includes validation
 * functionality, which allows custom validation rules to be applied to the input.
 * <p>
 * The input is requested in a loop, and the user is repeatedly prompted until valid
 * input is provided or until a valid input is empty (depending on the validation logic).
 * </p>
 * <p>
 * This class also provides methods to request more complex objects like
 * {@link Coordinates} and {@link Location} by prompting the user for individual
 * properties of these objects.
 * </p>
 */
public class ArgumentRequester {
    private static NewScannerManager newScannerManager;

    public static void setNewScannerManager(NewScannerManager newScannerManager){
        ArgumentRequester.newScannerManager = newScannerManager;
    }
    /**
     *
     * @param <T> data type (String, Double, Float, Long)
     * @param requested message for user
     * @param exceptionString exception message
     * @param parser lambda function to parse
     * @param validator validator for data
     * @return Optional
     */
    private static <T> Optional<T> requestValue(String requested, String exceptionString,
                                                Function<String, T> parser, Predicate<T> validator) {
        boolean validInput = false;
        T value = null;
        newScannerManager.saveActiveScanner();

        while (!validInput) {
            try {
                System.out.print(requested + ":\n>>> ");
                String input = newScannerManager.readLine();

                if (validator == null && input.isEmpty()) {
                    return Optional.empty();
                }

                value = parser.apply(input);
                if (validator == null || validator.test(value)) {
                    validInput = true;
                } else {
                    throw new InvalidPropertiesFormatException("Validation failed: " + exceptionString);
                }
            } catch (NoSuchElementException e){
                newScannerManager.activeConsole();
                System.out.println("Script has ended, write by yourself!");
            } catch (Exception e) {
                newScannerManager.activeConsole();
                System.out.println("Incorrect input format: " + e);
            }

        }

        newScannerManager.loadActiveScanner(); //!
        return Optional.of(value);
    }

    /**
     * Requests a {@code Double} value from the user, applying a specified validator.
     * If the input is invalid, the user will be prompted again until a valid value is provided.
     *
     * @param requested the prompt message to display to the user.
     * @param exceptionString the error message to display if the input fails validation.
     * @param validator a {@link Predicate} that defines the validation logic for the input.
     * @return the valid {@code Double} value entered by the user, or {@code null} if the input is empty and no validation is applied.
     */
    public static Optional<Double> requestDouble(String requested, String exceptionString, Predicate<Double> validator) {
        return requestValue(requested, exceptionString, Double::parseDouble, validator);
    }

    /**
     * Requests a {@code Float} value from the user, applying a specified validator.
     * If the input is invalid, the user will be prompted again until a valid value is provided.
     *
     * @param requested the prompt message to display to the user.
     * @param exceptionString the error message to display if the input fails validation.
     * @param validator a {@link Predicate} that defines the validation logic for the input.
     * @return the valid {@code Float} value entered by the user, or {@code null} if the input is empty and no validation is applied.
     */
    public static Optional<Float> requestFloat(String requested, String exceptionString, Predicate<Float> validator) {
        return requestValue(requested, exceptionString, Float::parseFloat, validator);
    }

    /**
     * Requests a {@code String} value from the user, applying a specified validator.
     * If the input is invalid, the user will be prompted again until a valid value is provided.
     *
     * @param requested the prompt message to display to the user.
     * @param exceptionString the error message to display if the input fails validation.
     * @param validator a {@link Predicate} that defines the validation logic for the input.
     * @return the valid {@code String} entered by the user, or {@code null} if the input is empty and no validation is applied.
     */
    public static Optional<String> requestString(String requested, String exceptionString, Predicate<String> validator) {
        return requestValue(requested, exceptionString, input -> input, validator);
    }

    /**
     * Requests a {@code Long} value from the user, applying a specified validator.
     * If the input is invalid, the user will be prompted again until a valid value is provided.
     *
     * @param requested the prompt message to display to the user.
     * @param exceptionString the error message to display if the input fails validation.
     * @param validator a {@link Predicate} that defines the validation logic for the input.
     * @return the valid {@code Long} value entered by the user, or {@code null} if the input is empty and no validation is applied.
     */
    public static Optional<Long> requestLong(String requested, String exceptionString, Predicate<Long> validator) {
        return requestValue(requested, exceptionString, Long::parseLong, validator);
    }

    /**
     * Requests a {@link Coordinates} object by prompting the user for the 'x' and 'y' coordinates.
     *
     * @return a {@link Coordinates} object containing the values entered by the user.
     */
    public static Coordinates requestCoordinates() throws IllegalArgumentException{
        Coordinates coordinates = new Coordinates();

        Optional<Double> x = requestDouble("Write 'x' -> Coordinates.x", "'x' must be Double and cannot be null", d -> true);
        Optional<Double> y = requestDouble("Write 'y' -> Coordinates.y", "'y' must be Double", d -> true);

        if (x.isPresent()) {
            coordinates.setX(x.get());
        } else {
            throw new IllegalArgumentException("Invalid input for 'x'. Coordinates cannot be null.");
        }

        if (y.isPresent()) {
            coordinates.setY(y.get());
        } else {
            throw new IllegalArgumentException("Invalid input for 'y'. Coordinates cannot be null.");
        }

        return coordinates;
    }

    /**
     * Requests a {@link Location} object by prompting the user for the 'x', 'y', 'z' coordinates and 'name'.
     *
     * @param requested the context or description of the location being requested, to be included in the prompts.
     * @return a {@link Location} object containing the values entered by the user, or {@code null} if the user does not provide valid values.
     */
    public static Location requestLocation(String requested) {
        Optional<Long> x = requestLong("Write 'x' -> Location.x (" + requested + ") || Press Enter: null -> Location(" + requested + ")", "'x' must be long", null);

        if (x.isEmpty()) {
            return null;
        }

        Optional<Float> y = requestFloat("Write 'y' -> Location.y (" + requested + ")", "'y' must be Float", Objects::nonNull);
        Optional<Float> z = requestFloat("Write 'z' -> Location.z (" + requested + ")", "'z' must be Float", Objects::nonNull);
        Optional<String> name = requestString("Write 'name' -> Location.name (" + requested + ")", "'name' must be String and not null or empty", s -> s != null && !s.isEmpty());

        Location loc = new Location();
        x.ifPresent(loc::setX);
        y.ifPresent(loc::setY);
        z.ifPresent(loc::setZ);
        name.ifPresent(loc::setName);

        return loc;
    }

}
