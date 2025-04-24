package org.javaLab6.utils.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

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

    public static CreateRouteDTO fromMap(LinkedHashMap<String, Object> map) {
        if (map == null) return null;
        CreateRouteDTO routeDTO = new CreateRouteDTO();
        routeDTO.setName((String) map.get("name"));
        routeDTO.setDistance((Double) map.get("distance"));
        routeDTO.setCoordinates(Coordinates.fromMap((LinkedHashMap<String, Object>) map.get("coordinates")));
        routeDTO.setFrom(Location.fromMap((LinkedHashMap<String, Object>) map.get("from")));
        routeDTO.setTo(Location.fromMap((LinkedHashMap<String, Object>) map.get("to")));
        return routeDTO;
    }
}
