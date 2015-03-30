package com.eventerzgz.interactor.population;

import java.util.List;

import com.eventerzgz.model.commons.Population;
import com.eventerzgz.model.exception.EventZgzException;


public class PopulationInteractor {

    public static List<Population> getPopulations() throws EventZgzException
    {
        List<Population> populationsList;

        if(PopulationMem.getInstance().getAllPopulations() != null && PopulationMem.getInstance().getAllPopulations().size() > 0){
            populationsList = PopulationMem.getInstance().getAllPopulations();
        }else{
            populationsList = new PopulationRest().getAllPopulations();
            PopulationMem.setPopulationsCached(populationsList);
        }

        return populationsList;
    }


}
