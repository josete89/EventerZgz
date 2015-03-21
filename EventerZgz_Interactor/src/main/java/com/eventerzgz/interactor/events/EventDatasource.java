package com.eventerzgz.interactor.events;

import java.util.List;

import com.eventerzgz.model.event.Event;

/**
 * Created by joseluis on 20/3/15.
 */
public interface EventDatasource
{


    public void createEvent(Event objEvent);
    public void updateEvent(Event objEvent);
    public void deleteEvent(String sId);

    public List<Event> getAllEvents();
    public Event findEventById(String sId);


}
