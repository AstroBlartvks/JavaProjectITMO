package org.AstrosLab.model;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Route implements Comparable<Route>  {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле может быть null
    private Location to; //Поле может быть null
    private double distance; //Значение поля должно быть больше 1

    @Override
    public int compareTo(Route other) {
        return Integer.compare(this.getId(), other.getId());
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", distance=" + this.distance +
                '}';
    }
}
