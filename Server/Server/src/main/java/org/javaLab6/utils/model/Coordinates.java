package org.javaLab6.utils.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

/**
 * The {@code Coordinates} class represents a set of coordinates with {@code x} and {@code y} values.
 * <p>
 * This class stores two coordinate values, {@code x} and {@code y}, both of which cannot be {@code null}.
 * It provides a {@link #toString()} method to get a string representation of the coordinates.
 */
@Setter
@Getter
public class Coordinates {

    /**
     * The x-coordinate of the location.
     * This field cannot be {@code null}.
     */
    private Double x;

    /**
     * The y-coordinate of the location.
     * This field cannot be {@code null}.
     */
    private Double y;

    /**
     * Returns a string representation of the coordinates, including the {@code x} and {@code y} values.
     *
     * @return A string representation of the coordinates.
     */
    @Override
    public String toString() {
        return "Location{" +
                "x=" + this.x +
                ", y=" + this.y +
                '}';
    }

    public static Coordinates fromMap(LinkedHashMap<String, Object> map) {
        if (map == null) return null;
        Coordinates coordinates = new Coordinates();
        coordinates.setX((Double) map.get("x"));
        coordinates.setY((Double) map.get("y"));
        return coordinates;
    }
}
