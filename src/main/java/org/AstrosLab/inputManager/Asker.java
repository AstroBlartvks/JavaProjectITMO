package org.AstrosLab.inputManager;

import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

@Getter
@Setter
public class Asker {
    private Exception exception;
    private ValidateRouteInput validRouteInput;

    public String askString(String text) {
        while (true) {
            System.out.print(text + ":\n>>> ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("String must not be Empty!");
                continue;
            } else if (input.contains("\\n")){
                System.out.println("Don't use \\n, because Prigozhin!");
                continue;
            } else {
                return input;
            }
        }
    }

    public String askDouble(String text) {
        while (true) {
            System.out.print(text + ":\n>>> ");
            Scanner scanner = new Scanner(System.in);

            try {
                String input = scanner.nextLine();

                if (input.isEmpty()){
                    return "null";
                }
                Double.parseDouble(input);
                return input;
            } catch (Exception e) {
                System.out.println("Your values is not a Double Exception{"+e.toString()+"}, try again!");
                continue;
            }

        }
    }

    public String askFloat(String text) {
        while (true) {
            System.out.print(text + ":\n>>> ");
            Scanner scanner = new Scanner(System.in);

            try {
                String input = scanner.nextLine();

                if (input.isEmpty()){
                    return "null";
                }
                Float.parseFloat(input);
                return input;
            } catch (Exception e) {
                System.out.println("Your values is not a Float Exception{"+e.toString()+"}, try again!");
                continue;
            }

        }
    }

    public String askLong(String text) {
        while (true) {
            System.out.print(text + ":\n>>> ");
            Scanner scanner = new Scanner(System.in);

            try {
                String input = scanner.nextLine();

                if (input.isEmpty()){
                    return "null";
                }

                Float.parseFloat(input);
                return input;
            } catch (Exception e) {
                System.out.println("Your values is not a Long Exception{"+e.toString()+"}, try again!");
                continue;
            }

        }
    }

    public String askCoordinates(String textForX, String textForY){
        String X = "";
        String Y = "";
        boolean xPassed = false;
        boolean yPassed = false;

        while (true){
            if (!xPassed){
                X = askDouble(textForX);
                if (X == "null"){
                    System.out.println("'X' can't be null, must be Double");
                    continue;
                }
                xPassed = true;
            }

            if (!yPassed) {
                Y = askDouble(textForY);
                if (Y == "null") {
                    System.out.println("'Y' can't be null, must be Double");
                    continue;
                }
                yPassed = true;
            }
            return X + "\n" + Y;
        }
    }

    public String askFromTo(String s, String s1, String s2, String s3) {
        String X = "";
        String Y = "";
        String Z = "";
        String Name = "";
        boolean xPassed = false;
        boolean yPassed = false;
        boolean zPassed = false;
        boolean NamePassed = false;

        while (true){
            if (!xPassed) {
                X = askLong(s);
                if (X == "null") {
                    return "null";
                }
                xPassed = true;
            }

            if (!yPassed) {
                Y = askFloat(s1);
                if (Y == "null") {
                    System.out.println("'Y' can't be null, must be Float");
                    continue;
                }
                yPassed = true;
            }

            if (!zPassed) {
                Z = askFloat(s2);
                if (Z == "null") {
                    System.out.println("'Z' can't be null, must be Float");
                    continue;
                }
                zPassed = true;
            }

            if (!NamePassed) {
                Name = askString(s3);
                NamePassed = true;
            }
            return X + "\n" + Y + "\n" + Z + "\n" + Name;
        }
    }

    public String askDistance(String text){
        String distance;

        while (true){
            distance = askDouble(text);
            if (distance == "null"){
                System.out.println("'distance' can't be null, must be double");
                continue;
            } else if (Double.parseDouble(distance) <= 1){
                System.out.println("'distance' must be > 1");
                continue;
            }

            return distance;
        }
    }

}
