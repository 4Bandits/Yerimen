package com.yerimen.server;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public interface Observable {

    List<Observer> observers = new ArrayList<>();

    default void addObserver(Observer observer){
        this.observers.add(observer);
    }

    default void deleteObserver(Observer observer){
        this.observers.remove(observer);
    }

    default void notify(JSONObject jsonObject){
        this.observers.forEach(observer -> observer.update(jsonObject));
    }

}


