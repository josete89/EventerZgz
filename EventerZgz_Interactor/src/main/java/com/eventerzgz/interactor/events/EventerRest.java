package com.eventerzgz.interactor.events;

import com.eventerzgz.interactor.BaseRest;
import com.eventerzgz.model.event.Event;
import com.eventerzgz.model.exception.EventZgzException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joseluis on 20/3/15.
 */
public class EventerRest extends BaseRest implements EventDatasource
{
    protected EventerRest(){

    }




    @Override
    public void createEvent(Event objEvent) throws EventZgzException
    {

    }

    @Override
    public void updateEvent(Event objEvent) throws EventZgzException
    {

    }

    @Override
    public void deleteEvent(String sId) throws EventZgzException
    {

    }

    @Override
    public List<Event> getAllEvents(EventInteractor.EventFilter... eventFilter) throws EventZgzException {
        List<Event> eventList = new ArrayList<>();

        String sUrl = "http://www.zaragoza.es/api/recurso/cultura-ocio/evento-zaragoza.xml?srsname=wgs84";

        String content = doHTTPGet(sUrl);

        return Event.doParse(content);
    }

    public static void main(String[] list) throws Exception {
        EventerRest eventerRest = new EventerRest();
        List<Event> eventList = eventerRest.getAllEvents();
        System.out.println(eventList);
    }


    @Override
    public Event findEventById(String sId) throws EventZgzException
    {
        return null;
    }
}
