package org.AstrosLab.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;

@Setter
@Getter
public class Route implements Comparable<Route>  {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле может быть null
    private Location to; //Поле может быть null
    private double distance; //Значение поля должно быть больше 1

    @Override
    public int compareTo(Route other) {
        return Comparator.comparingDouble(Route::getDistance)
                .thenComparingInt(Route::getId)
                .compare(this, other);
    }

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

    public String smallInfo(){
        return "Route{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", creationDate='" + this.creationDate + '\'' +
                '}';
    }
}
