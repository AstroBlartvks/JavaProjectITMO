package AstroLab.utils.model;

import AstroLab.grpc.RouteDto;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CreateRouteDto {
    private String name;
    private Coordinates coordinates;
    private Location from;
    private Location to;
    private Double distance;
    private String ownerLogin;

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

    public static CreateRouteDto getFromProtobuf(RouteDto routeDto) {
        CreateRouteDto createRouteDto = new CreateRouteDto();
        createRouteDto.setCoordinates(Coordinates.getFromDto(routeDto.getCoordinates()));

        if (routeDto.hasLocationFrom()) {
            createRouteDto.setFrom(Location.getFromDto(routeDto.getLocationFrom()));
        }

        if (routeDto.hasLocationTo()) {
            createRouteDto.setTo(Location.getFromDto(routeDto.getLocationTo()));
        }

        createRouteDto.setDistance(routeDto.getDistance());
        createRouteDto.setName(routeDto.getName());
        createRouteDto.setOwnerLogin(routeDto.getOwnerLogin());
        return createRouteDto;
    }

    public RouteDto.Builder convertToProtobuf() {
        RouteDto.Builder builder = RouteDto.newBuilder()
                .setCoordinates(getCoordinates().convertToProtobuf())
                .setDistance(getDistance())
                .setName(getName())
                .setOwnerLogin(getOwnerLogin());

        if (getFrom() != null) {
            builder.setLocationFrom(getFrom().convertToProtobuf());
        }

        if (getTo() != null) {
            builder.setLocationFrom(getTo().convertToProtobuf());
        }

        return builder;
    }
}
