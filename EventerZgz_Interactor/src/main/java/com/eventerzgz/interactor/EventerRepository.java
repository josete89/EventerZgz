package com.eventerzgz.interactor;

import com.eventerzgz.model.Event;

/**
 * Created by joseluis on 20/3/15.
 */
public interface EventerRepository
{


    public void createEvent(Event objEvent);
    public void updateEvent(Event objEvent);
    public void deleteEvent(String sId);


}
