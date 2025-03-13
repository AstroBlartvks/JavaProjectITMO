package org.AstrosLab.inputManager;

import org.AstrosLab.model.Coordinates;
import org.AstrosLab.model.Location;

import java.util.Objects;
import java.util.function.Predicate;

public class ArgumentRequester {

    public static Double requestDouble(String requested, String exceptionString, Predicate<Double> validator) {
        ScannerManager.saveScanner();
        while (true) {
            System.out.print(requested + ":\n>>> ");
            String input = ScannerManager.readline();

            if (input.isEmpty() && validator == null) {
                return null;
            }

            try {
                Double number = Double.parseDouble(input);
                if (validator == null || validator.test(number)) {
                    ScannerManager.loadScanner();
                    return number;
                } else {
                    System.out.println("Validate exception: "+exceptionString);
                }
            } catch (NumberFormatException e) {
                if (input.isEmpty()) {
                    System.out.println("Incorrect input format. Must be 'Double', can't be 'null'");
                } else {
                    System.out.println("Incorrect input format (must be 'Double'):" + e);
                }
            } catch (Exception e){
                System.out.println("Unexpected error" + e);
            }
            ScannerManager.setConsoleScanner();
        }
    }

    public static Float requestFloat(String requested, String exceptionString, Predicate<Float> validator) {
        ScannerManager.saveScanner();
        while (true) {
            System.out.print(requested + ":\n>>> ");
            String input = ScannerManager.readline();

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

    public static Integer requestInteger(String requested, String exceptionString, Predicate<Integer> validator) {
        ScannerManager.saveScanner();
        while (true) {
            System.out.print(requested + ":\n>>> ");
            String input = ScannerManager.readline();

            if (input.isEmpty() && validator == null) {
                return null;
            }

            try {
                Integer number = Integer.parseInt(input);
                if (validator == null || validator.test(number)) {
                    ScannerManager.loadScanner();
                    return number;
                } else {
                    System.out.println("Validate exception: "+exceptionString);
                }
            } catch (NumberFormatException e) {
                if (input.isEmpty()) {
                    System.out.println("Incorrect input format. Must be 'Integer', can't be 'null'");
                }else{
                    System.out.println("Incorrect input format (must be 'Integer'): "+e);
                }
            } catch (Exception e){
                System.out.println("Unexpected error" + e);
            }
            ScannerManager.setConsoleScanner();
        }
    }

    public static String requestString(String requested, String exceptionString, Predicate<String> validator){
        ScannerManager.saveScanner();
        while (true) {
            System.out.print(requested + ":\n>>> ");
            String input = ScannerManager.readline();

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

    public static Long requestLong(String requested, String exceptionString, Predicate<Long> validator){
        ScannerManager.saveScanner();
        while (true) {
            System.out.print(requested + ":\n>>> ");
            String input = ScannerManager.readline();

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

    public static Coordinates requestCoordinates(){
        Coordinates coords = new Coordinates();
        Double x = requestDouble("Write 'x' -> Coordinates.x", "'x' must be Double And Coordinates can't bee null", Objects::nonNull);
        Double y = requestDouble("Write 'y' -> Coordinates.y", "'y' must be Double", Objects::nonNull);
        coords.setX(x);
        coords.setY(y);
        return coords;
    }

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
