package org.AstroLab.utils.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateRouteDTO {
    private String name;
    private Coordinates coordinates;
    private Location from;
    private Location to;
    private Double distance;

    @Override
    public String toString() {
        return "DataTransferObject(Route){" +
                ", name='" + this.name + '\'' +
                ", distance=" + this.distance +
                ", from=" + this.from +
                ", to=" + this.to +
                ", coordinates=" + this.coordinates +
                '}';
    }
}
