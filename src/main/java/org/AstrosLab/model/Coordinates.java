package org.AstrosLab.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Coordinates {
    private Double x; //Поле не может быть null
    private Double y; //Поле не может быть null
    public Coordinates(Double x, Double y){
        this.x = x;
        this.y = y;
    }
}
