package AstroLab.utils.model;

import AstroLab.grpc.CoordinatesDto;
import lombok.Getter;
import lombok.Setter;

/**
 * The {@code Coordinates} class represents a set of coordinates with {@code x} and {@code y} values.
 *
 *  <p>
 * This class stores two coordinate values, {@code x} and {@code y}, both of which cannot be {@code null}.
 * It provides a {@link #toString()} method to get a string representation of the coordinates.
 */
@SuppressWarnings("checkstyle:RegexpMultiline")
@Setter
@Getter
public class Coordinates {

    /**
     * The x-coordinate of the location.
     * This field cannot be {@code null}.
     */
    @SuppressWarnings("checkstyle:MemberName")
    private Double x;

    /**
     * The y-coordinate of the location.
     * This field cannot be {@code null}.
     */
    @SuppressWarnings("checkstyle:MemberName")
    private Double y;


    private int id;

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

    public static Coordinates getFromDto(CoordinatesDto coordinatesDto) {
        Coordinates coordinates = new Coordinates();
        coordinates.setId(coordinatesDto.getId());
        coordinates.setX(coordinatesDto.getX());
        coordinates.setY(coordinatesDto.getY());
        return coordinates;
    }

    public CoordinatesDto.Builder convertToProtobuf() {
        return CoordinatesDto.newBuilder()
                .setX(getX())
                .setY(getY())
                .setId(getId());
    }
}
