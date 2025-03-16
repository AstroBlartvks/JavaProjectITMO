package org.javaLab5.collection;

import lombok.Getter;
import org.javaLab5.model.Route;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents a custom collection of Route objects.
 * This collection ensures uniqueness of Route IDs and provides various utility methods
 * for managing, updating, and retrieving routes.
 */
@Getter
public class CustomCollection {
    private final Set<Route> collection = new TreeSet<>();

    /**
     * Adds a new Route to the collection.
     * Ensures that the route ID is unique.
     *
     * @param route the Route object to be added
     * @throws Exception if the ID is already present in the collection
     */
    public void addElement(Route route) throws Exception {
        if (this.containsID(route.getId())) {
            throw new Exception("'id' must be unique, it can't be: " + route.getId() +
                    ".\nNew Route: " + route + "\nOld Route: " + this.getRouteInsideByID(route.getId()));
        }
        this.collection.add(route);
    }

    /**
     * Updates an existing Route by removing the old entry with the same ID and adding the new one.
     *
     * @param route the Route object with updated data
     */
    public void updateElement(Route route) {
        this.collection.removeIf(r -> r.getId() == route.getId());
        this.collection.add(route);
    }

    /**
     * Removes a Route from the collection by its ID.
     *
     * @param id the ID of the Route to be removed
     */
    public void removeByID(int id) {
        this.collection.removeIf(r -> r.getId() == id);
    }

    /**
     * Clears all elements from the collection.
     */
    public void clear() {
        this.collection.clear();
    }

    /**
     * Retrieves descriptions of all Routes in the collection.
     *
     * @return a formatted string with descriptions of all routes
     */
    public String getRoutesDescriptions() {
        return this.collection.stream()
                .map(Route::toString)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Retrieves summarized descriptions of all Routes.
     *
     * @return a formatted string with summarized route information
     */
    public String getRoutesSmallDescriptions() {
        return this.collection.stream()
                .map(Route::smallInfo)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Retrieves a Route by its ID.
     *
     * @param id the ID of the Route to find
     * @return the matching Route object, or null if not found
     */
    public Route getRouteInsideByID(int id) {
        return this.collection.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Generates a new unique ID for a Route.
     *
     * @return a unique integer ID
     */
    public int getNewID() {
        Set<Integer> ids = this.collection.stream()
                .map(Route::getId)
                .collect(Collectors.toCollection(TreeSet::new));

        return IntStream.range(1, ids.size() + 2)
                .filter(i -> !ids.contains(i))
                .findFirst()
                .orElse(ids.size() + 1);
    }

    /**
     * Checks whether a Route with the given ID exists in the collection.
     *
     * @param id the ID to check
     * @return true if the ID exists, false otherwise
     */
    public boolean containsID(int id) {
        return this.collection.stream()
                .anyMatch(r -> r.getId() == id);
    }

    /**
     * Counts the number of Routes that have the specified distance.
     *
     * @param distance the distance to match
     * @return the number of matching Routes
     */
    public int countByDistance(double distance) {
        return (int) this.collection.stream()
                .filter(r -> r.getDistance() == distance)
                .count();
    }

    /**
     * Counts the number of Routes with a distance greater than the specified value.
     *
     * @param distance the distance threshold
     * @return the count of Routes with greater distance
     */
    public int countGreaterThanDistance(double distance) {
        return (int) this.collection.stream()
                .filter(r -> r.getDistance() > distance)
                .count();
    }

    /**
     * Retrieves all Route distances sorted in descending order.
     *
     * @return a string representation of distances in descending order
     */
    public String printFieldDescendingDistance() {
        return (this.collection.stream()
                .map(Route::getDistance)
                .sorted(Comparator.reverseOrder())
                .toList()).toString();
    }

    /**
     * Returns a string representation of the collection with a summary of its elements.
     *
     * @return a formatted string describing the collection
     */
    @Override
    public String toString() {
        return "Collection (TreeSet<Route>):\n" +
                "Have " + this.collection.size() + " items <Route>:\n" +
                this.getRoutesSmallDescriptions();
    }
}
