package org.javaLab5.files;

import org.javaLab5.collection.CustomCollection;

/**
 * The {@code Writer} class is responsible for writing data to a file whose
 * path is specified through an environment variable. The class delegates the
 * actual writing process to an instance of {@link WriteHandler}.
 * <p>
 * The path to the file is retrieved from the environment variable defined by
 * the {@link JavaFileStrings#JAVAEE} constant. If the environment variable
 * is not set, an exception is thrown.
 * </p>
 *
 * @see WriteHandler
 * @see CustomCollection
 * @see JavaFileStrings
 */
public class Writer {
    private final WriteHandler writeHandler;

    /**
     * Constructs a {@code Writer} instance with a specified {@link WriteHandler}
     * to handle file writing operations.
     *
     * @param writeHandler the {@link WriteHandler} used to write data to the file.
     */
    public Writer(WriteHandler writeHandler){
        this.writeHandler = writeHandler;
    }

    /**
     * Writes data from the provided {@link CustomCollection} to a file. The file
     * path is obtained from the environment variable defined by {@link JavaFileStrings#JAVAEE}.
     * <p>
     * If the environment variable is not set or the path cannot be retrieved, an
     * exception is thrown.
     * </p>
     *
     * @param collection the {@link CustomCollection} containing the data to be written to the file.
     * @throws Exception if the environment variable is not found or if an error occurs while writing to the file.
     */
    public void writeToEnv(CustomCollection collection) throws Exception {
        String Path = System.getenv(JavaFileStrings.JAVAEE);

        if (Path == null){
            throw new IllegalStateException("The path to the file in the environment variable '" + JavaFileStrings.JAVAEE + "' was not found!");
        }

        writeHandler.writeFile(Path, collection);
    }
}
