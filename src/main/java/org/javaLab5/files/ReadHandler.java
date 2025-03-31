package org.javaLab5.files;

import org.javaLab5.collection.CustomCollection;

/**
 * Abstraction over file reading so that you can set different ways of reading in the future
 * Not only Json
 * Such as Yaml, XML, etc.
 */
public abstract class ReadHandler {
    /**
     * Read the collection from a file
     * @param Path path of file
     * @return {@link CustomCollection} return CustomCollection object parsed from file
     * @throws Exception while reading mb exceptions
     */
    abstract public CustomCollection readFile(String Path) throws Exception;
}
