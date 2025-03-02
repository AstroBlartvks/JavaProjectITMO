package org.AstrosLab.collectrion;

import org.AstrosLab.model.Route;

import java.util.ArrayList;
import java.util.TreeSet;

public class customCollection {
    private TreeSet<Route> collection;
//    private java.util.Date creationDate = null; Future

    public customCollection(){
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

    @Override
    public String toString(){
        return "Collection (TreeSet<Route>):\n" +
                "Have " + this.collection.size() + " items <Route>:\n" +
                this.getRoutesSmallDescriptions();
    }
}
