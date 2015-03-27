package com.eventerzgz.presenter.listevents;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.commons.Population;
import com.eventerzgz.model.event.Event;
import com.eventerzgz.presenter.BasePresenterIface;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by JavierArroyo on 21/3/15.
 */
public interface ListEventsIface extends BasePresenterIface {

    public void fetchedEvents(List<Event> event);
    public void fetchedEventsOrder(Map<Date,List<Event>> orderEvents);
    public void fetchedCategories(List<Category> event);
    public void fetchedPopulation(List<Population> populationList);
}
