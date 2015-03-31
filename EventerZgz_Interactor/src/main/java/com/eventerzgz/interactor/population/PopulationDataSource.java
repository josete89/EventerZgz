package com.eventerzgz.interactor.population;

import java.util.List;

import com.eventerzgz.model.commons.Population;
import com.eventerzgz.model.exception.EventZgzException;


public interface PopulationDataSource {

     List<Population> getAllPopulations() throws EventZgzException;

}
