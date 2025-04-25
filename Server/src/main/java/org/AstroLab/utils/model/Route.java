package org.AstroLab.utils.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.Date;

/**
 * The {@code Route} class represents a route with information such as ID, name, coordinates, creation date,
 * locations (from and to), and distance.
 * <p>
 * This class implements the {@link Comparable} interface to allow comparison of {@code Route} objects based on
 * distance and ID. It also provides a method to return a summary of the route's information.
 */
@Setter
@Getter
public class Route implements Comparable<Route> {

    /**
     * The unique identifier of the route. This value must be greater than 0 and should be automatically generated.
     */
    private int id;

    /**
     * The name of the route. This value cannot be {@code null} and the string cannot be empty.
     */
    private String name;

    /**
     * The coordinates of the route. This field cannot be {@code null}.
     */
    private Coordinates coordinates;

    /**
     * The creation date of the route. This field cannot be {@code null} and should be automatically generated.
     */
    private Date creationDate;

    /**
     * The location from which the route starts. This field may be {@code null}.
     */
    private Location from;

    /**
     * The location where the route ends. This field may be {@code null}.
     */
    private Location to;

    /**
     * The distance of the route. This value must be greater than 1.
     */
    private double distance;

    /**
     * Compares this route with another route based on the distance. If the distances are equal, it compares by ID.
     *
     * @param other The route to compare with.
     * @return A negative integer, zero, or a positive integer as this route is less than, equal to, or greater than
     *         the specified route.
     */
    @Override
    public int compareTo(Route other) {
        return Comparator.comparing(Route::getName)
                .compare(this, other);
    }

    /**
     * Returns a string representation of this route, including the ID, name, distance, creation date,
     * from and to locations, and coordinates.
     *
     * @return A string representation of this route.
     */
    @Override
    public String toString() {
        return "Route{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", distance=" + this.distance +
                ", creationDate='" + this.creationDate + '\'' +
                ", from=" + this.from +
                ", to=" + this.to +
                ", coordinates=" + this.coordinates +
                '}';
    }

    /**
     * Returns a brief summary of this route, including the ID, name, and creation date.
     *
     * @return A string containing a brief summary of the route.
     */
    public String smallInfo() {
        return "Route{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", creationDate='" + this.creationDate + '\'' +
                '}';
    }

    /**
     * Set data from RouteDTO
     *
     * @param routeDTO The 'RouteDataTransferObject' object that we get information from
     */
    public void setFromRouteDataTransferObject(CreateRouteDTO routeDTO){
        setName(routeDTO.getName());
        setCoordinates(routeDTO.getCoordinates());
        setFrom(routeDTO.getFrom());
        setTo(routeDTO.getTo());
        setDistance(routeDTO.getDistance());
    }
}
