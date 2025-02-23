package org.AstrosLab.collectrion;

import org.AstrosLab.model.Route;

import java.util.TreeSet;

public class customCollection {
    TreeSet<Route> collection;

    public customCollection(){
        this.collection = new TreeSet<Route>();
    }

    public void addElement(Route r) {
        this.collection.add(r);
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

    public void printALl(){
        for (Route r : this.collection) {
            System.out.println(r);
        }
    }

    private Route getRouteInsideByID(int id) {
        for (Route r : this.collection) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

}
