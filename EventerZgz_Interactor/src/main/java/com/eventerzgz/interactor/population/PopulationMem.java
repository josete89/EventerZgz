package com.eventerzgz.interactor.population;

import java.util.List;

import com.eventerzgz.model.commons.Population;
import com.eventerzgz.model.exception.EventZgzException;


public class PopulationMem implements PopulationDataSource {

    private static PopulationMem instance;
    private List<Population> eventCached;

    private PopulationMem(){

    }

    public static PopulationMem getInstance(){
        if(instance == null){
            synchronized (PopulationMem.class){
                if(instance == null){
                    instance  = new PopulationMem();
                }
            }
        }
        return instance;
    }

    public static void setPopulationsCached(List<Population> eventCached) {
        getInstance().eventCached = eventCached;
    }

    @Override
    public List<Population> getAllPopulations() throws EventZgzException {
        return eventCached;
    }
}
