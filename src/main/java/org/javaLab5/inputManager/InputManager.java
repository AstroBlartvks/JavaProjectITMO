package org.javaLab5.inputManager;

/**
 * The {@code InputManager} class provides methods to manage and handle user input.
 * It interacts with the {@link ScannerManager} class to read user input and check if input is available.
 * <p>
 * This class contains methods for checking if there is more input available and reading the next line of input from the user.
 */
public class InputManager {

    /**
     * Checks if there is more input available from the user.
     * <p>
     * It prints the prompt ">>>" and waits for the user to provide input.
     *
     * @return {@code true} if there is more input available, {@code false} otherwise.
     */
    public boolean hasNextInput() {
        System.out.print(">>> ");
        return ScannerManager.hasNextLine();
    }

    /**
     * Reads the next line of input from the user.
     * <p>
     * This method waits for the user to input a line of text and then returns the entered string.
     *
     * @return The input line as a {@code String}.
     */
    public String input() {
        return ScannerManager.readLine();
    }

}
