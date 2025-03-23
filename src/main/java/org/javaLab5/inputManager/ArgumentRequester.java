package org.javaLab5.inputManager;

import org.javaLab5.model.Coordinates;
import org.javaLab5.model.Location;

import java.util.Objects;
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

    /**
     * Requests a {@code Double} value from the user, applying a specified validator.
     * If the input is invalid, the user will be prompted again until a valid value is provided.
     *
     * @param requested the prompt message to display to the user.
     * @param exceptionString the error message to display if the input fails validation.
     * @param validator a {@link Predicate} that defines the validation logic for the input.
     * @return the valid {@code Double} value entered by the user, or {@code null} if the input is empty and no validation is applied.
     */
    public static Double requestDouble(String requested, String exceptionString, Predicate<Double> validator) {
        ScannerManager.saveScanner();
        while (true) {
            System.out.print(requested + ":\n>>> ");
            String input = ScannerManager.readLine();

            if (input.isEmpty() && validator == null) {
                return null;
            }

            try {
                Double number = Double.parseDouble(input);
                if (validator == null || validator.test(number)) {
                    ScannerManager.loadScanner();
                    return number;
                } else {
                    System.out.println("Validate exception: " + exceptionString);
                }
            } catch (NumberFormatException e) {
                if (input.isEmpty()) {
                    System.out.println("Incorrect input format. Must be 'Double', can't be 'null'");
                } else {
                    System.out.println("Incorrect input format (must be 'Double'):");
                }
            } catch (Exception e){
                System.out.println("Unexpected error" + e);
            }
            ScannerManager.setConsoleScanner();
        }
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
    public static Float requestFloat(String requested, String exceptionString, Predicate<Float> validator) {
        ScannerManager.saveScanner();
        while (true) {
            System.out.print(requested + ":\n>>> ");
            String input = ScannerManager.readLine();

            if (input.isEmpty() && validator == null) {
                return null;
            }

            try {
                Float number = Float.parseFloat(input);
                if (validator == null || validator.test(number)) {
                    ScannerManager.loadScanner();
                    return number;
                } else {
                    System.out.println("Validate exception: " + exceptionString);
                }
            } catch (NumberFormatException e) {
                if (input.isEmpty()) {
                    System.out.println("Incorrect input format. Must be 'Float', can't be 'null'");
                } else{
                    System.out.println("Incorrect input format (must be 'Float'): "+e);
                }
            } catch (Exception e){
                System.out.println("Unexpected error" + e);
            }
            ScannerManager.setConsoleScanner();
        }
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
    public static String requestString(String requested, String exceptionString, Predicate<String> validator){
        ScannerManager.saveScanner();
        while (true) {
            System.out.print(requested + ":\n>>> ");
            String input = ScannerManager.readLine();

            if (input.isEmpty() && validator == null) {
                return null;
            }

            if (validator == null || validator.test(input)) {
                ScannerManager.loadScanner();
                return input;
            } else {
                System.out.println("Validate exception: " + exceptionString);
            }
            ScannerManager.setConsoleScanner();
        }
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
    public static Long requestLong(String requested, String exceptionString, Predicate<Long> validator){
        ScannerManager.saveScanner();
        while (true) {
            System.out.print(requested + ":\n>>> ");
            String input = ScannerManager.readLine();

            if (input.isEmpty() && validator == null) {
                return null;
            }

            try {
                Long number = Long.parseLong(input);
                if (validator == null || validator.test(number)) {
                    ScannerManager.loadScanner();
                    return number;
                } else {
                    System.out.println("Validate exception: "+exceptionString);
                }
            } catch (NumberFormatException e) {
                if (input.isEmpty()) {
                    System.out.println("Incorrect input format. Must be 'Long', can't be 'null'");
                } else{
                    System.out.println("Incorrect input format (must be 'Long'): " + e);
                }
            } catch (Exception e){
                System.out.println("Unexpected error" + e);
            }
            ScannerManager.setConsoleScanner();
        }
    }

    /**
     * Requests a {@link Coordinates} object by prompting the user for the 'x' and 'y' coordinates.
     *
     * @return a {@link Coordinates} object containing the values entered by the user.
     */
    public static Coordinates requestCoordinates(){
        Coordinates coordinates = new Coordinates();
        Double x = requestDouble("Write 'x' -> Coordinates.x", "'x' must be Double And Coordinates can't be null", Objects::nonNull);
        Double y = requestDouble("Write 'y' -> Coordinates.y", "'y' must be Double", Objects::nonNull);
        coordinates.setX(x);
        coordinates.setY(y);
        return coordinates;
    }

    /**
     * Requests a {@link Location} object by prompting the user for the 'x', 'y', 'z' coordinates and 'name'.
     *
     * @param requested the context or description of the location being requested, to be included in the prompts.
     * @return a {@link Location} object containing the values entered by the user, or {@code null} if the user does not provide valid values.
     */
    public static Location requestLocation(String requested){
        Long x = requestLong("Write 'x' -> Location.x ("+requested+") || Press Enter: null -> Location("+requested+")", "'x' must be long", null);
        if (x == null){
            return null;
        }

        Float y = requestFloat("Write 'y' -> Location.y ("+requested+")", "'y' must be Float", Objects::nonNull);
        Float z = requestFloat("Write 'z' -> Location.z ("+requested+")", "'z' must be Float", Objects::nonNull);
        String name = requestString("Write 'name' -> Location.name ("+requested+")", "'name' must be String and not null or empty", s -> s != null && !s.isEmpty());

        Location loc = new Location();
        loc.setX(x);
        loc.setY(y);
        loc.setZ(z);
        loc.setName(name);
        return loc;
    }
}
