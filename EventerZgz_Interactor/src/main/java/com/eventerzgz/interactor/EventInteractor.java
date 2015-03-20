package com.eventerzgz.interactor;

/**
 * Created by joseluis on 20/3/15.
 */
public class EventInteractor {


    public static EventerRepository getInstance(){

        //Decidir la instancia a usar..

        return new EventerRest();
    }
}
