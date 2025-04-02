package org.javaLab5.model;

import lombok.Getter;
import lombok.Setter;

/**
 * The {@code Location} class represents a location with coordinates and a name.
 * <p>
 * This class stores the coordinates of the location (x, y, z) and the name of the location.
 * It provides a {@link #toString()} method to get a string representation of the location.
 */
@Setter
@Getter
public class Location {

    /**
     * The x-coordinate of the location.
     * This field is required but can be any value.
     */
    private long x;

    /**
     * The y-coordinate of the location.
     * This field cannot be {@code null}.
     */
    private Float y;

    /**
     * The z-coordinate of the location.
     * This field cannot be {@code null}.
     */
    private Float z;

    /**
     * The name of the location.
     * This field cannot be {@code null}.
     */
    private String name;

    /**
     * Returns a string representation of this location, including the x, y, z coordinates and the name.
     *
     * @return A string representation of the location.
     */
    @Override
    public String toString() {
        return "Location{" +
                "x=" + this.x +
                ", y=" + this.y +
                ", z=" + this.z +
                ", name='" + this.name +
                "'}";
    }
}
