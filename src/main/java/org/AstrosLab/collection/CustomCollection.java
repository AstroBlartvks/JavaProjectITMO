package org.AstrosLab.collection;

import lombok.Getter;
import org.AstrosLab.model.Route;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class CustomCollection {
    private final Set<Route> collection = new TreeSet<Route>();

    public void addElement(Route r) throws Exception{
        if (this.containsID(r.getId())){
            throw new Exception("'id' must be unique, it can't be: " + r.getId() + ".\nNew Route: " + r.toString() + "\nOld Route: " + this.getRouteInsideByID(r.getId()).toString());
        }
        this.collection.add(r);
    }

    public void updateElement(Route route) {
        this.collection.removeIf(r -> r.getId() == route.getId());
        this.collection.add(route);
    }

    public void removeByID(int id) {
        this.collection.removeIf(r -> r.getId() == id);
    }

    public void clear(){
        this.collection.clear();
    }

    public String getRoutesDescriptions() {
        return this.collection.stream()
                .map(Route::toString)
                .collect(Collectors.joining("\n"));
    }

    public String getRoutesSmallDescriptions() {
        return this.collection.stream()
                .map(Route::smallInfo)
                .collect(Collectors.joining("\n"));
    }

    public Route getRouteInsideByID(int id) {
        return this.collection.stream()
                .filter(r -> r.getId() == id).
                findFirst().
                orElse(null);
    }

    public int getNewID() {
        Set<Integer> ids = this.collection.stream()
                .map(Route::getId)
                .collect(Collectors.toCollection(TreeSet::new));

        return IntStream.range(1, ids.size() + 2)
                .filter(i -> !ids.contains(i))
                .findFirst()
                .orElse(ids.size() + 1);
    }

    public boolean containsID(int id) {
        return this.collection.stream()
                .anyMatch(r -> r.getId() == id);
    }

    public int countByDistance(double distance) {
        return (int) this.collection.stream()
                .filter(r -> r.getDistance() == distance)
                .count();
    }
    public int countGreaterThanDistance(double distance) {
        return (int) this.collection.stream()
                .filter(r -> r.getDistance() > distance)
                .count();
    }

    public String printFieldDescendingDistance() {
        return (this.collection.stream()
                .map(Route::getDistance)
                .sorted(Comparator.reverseOrder())
                .toList()).toString();
    }

    @Override
    public String toString(){
        return "Collection (TreeSet<Route>):\n" +
                "Have " + this.collection.size() + " items <Route>:\n" +
                this.getRoutesSmallDescriptions();
    }
}
