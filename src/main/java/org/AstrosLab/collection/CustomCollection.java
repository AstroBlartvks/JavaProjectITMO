package org.AstrosLab.collection;

import org.AstrosLab.model.Route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class CustomCollection {
    private TreeSet<Route> collection;

    public CustomCollection(){
        this.collection = new TreeSet<Route>();
    }

    public void addElement(Route r) {
        this.collection.add(r);
    }

    public int size(){
        return collection.size();
    }

    public void updateElement(Route route) {
        Route r = getRouteInsideByID(route.getId());
        if (r != null){
            this.collection.remove(r);
            this.collection.add(route);
        }
    }

    public void removeByID(int id){
        Route r = getRouteInsideByID(id);
        if (r != null){
            this.collection.remove(r);
        }
    }

    public void clear(){
        this.collection.clear();
    }

    public String getRoutesDescriptions(){
        StringBuilder text = new StringBuilder();
        for (Route r : this.collection) {
            text.append(r.toString()).append("\n");
        }
        return text.toString();
    }

    public String getRoutesSmallDescriptions(){
        StringBuilder text = new StringBuilder();
        for (Route r : this.collection) {
            text.append(r.smallInfo()).append("\n");
        }
        return text.toString();
    }

    public Route getRouteInsideByID(int id) {
        for (Route r : this.collection) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    public int getNewID(){
        int newID = 0;
        for (Route r : this.collection) {
            if (r.getId() > newID){
                newID = r.getId();
            }
        }
        return newID + 1;
    }

    public boolean containsID(int id){
        for (Route r : this.collection) {
            if (r.getId() == id){
                return true;
            }
        }
        return false;
    }

    public int countByDistance(double distance){
        int count = 0;
        for (Route r : this.collection) {
            if (r.getDistance() == distance){
                count++;
            }
        }
        return count;
    }

    public int greaterThanDistance(double distance){
        int count = 0;
        for (Route r : this.collection) {
            if (r.getDistance() > distance){
                count++;
            }
        }
        return count;
    }

    public ArrayList<Double> printFieldDescendingDistance(){
        ArrayList<Double> distances = new ArrayList<Double>();
        for (Route r : this.collection) {
            distances.add(r.getDistance());
        }
        Collections.sort(distances, Collections.reverseOrder());
        return distances;
    }

    @Override
    public String toString(){
        return "Collection (TreeSet<Route>):\n" +
                "Have " + this.collection.size() + " items <Route>:\n" +
                this.getRoutesSmallDescriptions();
    }
}
