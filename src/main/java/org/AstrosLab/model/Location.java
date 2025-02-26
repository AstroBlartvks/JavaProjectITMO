package org.AstrosLab.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Location {
    private long x;
    private Float y; //Поле не может быть null
    private Float z; //Поле не может быть null
    private String name; //Поле не может быть null

    @Override
    public String toString() {
        return "Location{" +
                "x=" + this.x +
                ", y=" + this.y +
                ", z=" + this.z +
                ", name=" + this.name +
                '}';
    }
}
