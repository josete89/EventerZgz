package com.eventerzgz.interactor.events;

import java.util.List;

import com.eventerzgz.model.event.Event;
import com.eventerzgz.model.exception.EventZgzException;

/**
 * Created by joseluis on 20/3/15.
 */
public interface EventDatasource
{


     void createEvent(Event objEvent) throws EventZgzException;
     void updateEvent(Event objEvent) throws EventZgzException;
     void deleteEvent(String sId) throws EventZgzException;

     List<Event> getAllEvents(EventInteractor.EventFilter... eventFilter) throws EventZgzException;
     Event findEventById(String sId) throws EventZgzException;


}
