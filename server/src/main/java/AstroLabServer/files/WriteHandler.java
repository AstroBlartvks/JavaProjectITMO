package AstroLabServer.files;

import AstroLabServer.collection.CustomCollection;

/**
 * Abstraction over file reading so that you can set different ways of writing in the future
 * Not only Json
 * Such as Yaml, XML, etc.
 */
public abstract class WriteHandler {
    /**.
     * Write the collection into a file
     *
     * @param path path of file
     * @param collection CustomCollection object to write in file
     * @throws Exception while writing mb exceptions
     */
    abstract void writeFile(String path, CustomCollection collection) throws Exception;
}
