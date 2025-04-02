package org.javaLab5.files;
import org.javaLab5.collection.CustomCollection;


/**
 * The {@code Reader} class is responsible for reading data from a file whose
 * path is provided through an environment variable. The class relies on an
 * instance of {@link ReadHandler} to handle the file reading and data parsing.
 * <p>
 * The path to the file is retrieved from the environment variable defined by
 * the {@link JavaFileStrings#JAVAEE} constant. If the environment variable
 * is not set or if the file cannot be read, an exception is thrown.
 * </p>
 *
 * @see ReadHandler
 * @see CustomCollection
 * @see JavaFileStrings
 */
public class Reader {
    private final ReadHandler readHandler;

    /**
     * Constructs a {@code Reader} instance with a specified {@link ReadHandler}
     * to handle file reading operations.
     *
     * @param readHandler the {@link ReadHandler} used to read data from the file.
     */
    public Reader(ReadHandler readHandler){
        this.readHandler = readHandler;
    }

    /**
     * Reads data from a file whose path is provided by the environment variable
     * defined by {@link JavaFileStrings#JAVAEE}. The data is processed using the
     * provided {@link ReadHandler}.
     *
     * @return a {@link CustomCollection} containing the data read from the file.
     * @throws Exception if the environment variable is not found or if an error
     *         occurs while reading the file.
     */
    public CustomCollection readFromEnv() throws Exception {
        String Path = System.getenv(JavaFileStrings.JAVAEE);

        if (Path == null){
            throw new Exception("The path to the file in the environment variable '" + JavaFileStrings.JAVAEE + "' was not found!");
        }

        return readHandler.readFile(Path);
    }
}

