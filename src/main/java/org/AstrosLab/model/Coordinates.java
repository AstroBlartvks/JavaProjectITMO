package org.AstrosLab.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Coordinates {
    private Double x; //Поле не может быть null
    private Double y; //Поле не может быть null
    @Override
    public String toString() {
        return "Location{" +
                "x=" + this.x +
                ", y=" + this.y +
                '}';
    }
}
