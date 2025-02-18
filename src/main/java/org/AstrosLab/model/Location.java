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
    
    public Location(long x, Float y, Float z, String name){
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }
}
