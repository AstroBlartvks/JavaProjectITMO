package org.javaLab5.model;

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
}
