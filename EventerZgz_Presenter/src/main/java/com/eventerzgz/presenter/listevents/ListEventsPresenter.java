package com.eventerzgz.presenter.listevents;

import com.eventerzgz.model.event.Event;
import com.eventerzgz.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JavierArroyo on 21/3/15.
 */
public class ListEventsPresenter extends BasePresenter {

    public final ListEventsIface listEventsIface;

    public ListEventsPresenter(ListEventsIface listEventsIface) {
        this.listEventsIface = listEventsIface;
    }

    public static Event eventDummy(){
        Event eventDummy = new Event();

        eventDummy.setdEndDate(new Date());
        eventDummy.setdLastUpdate(new Date());
        eventDummy.setsDescription("Description");
        eventDummy.setsTicketType("Libre");
        eventDummy.setsTitle("Titulo");
        return eventDummy;
    }

    public void getEventList(){
        List<Event> eventList = new ArrayList<>();
        eventList.add(eventDummy());
        eventList.add(eventDummy());
        eventList.add(eventDummy());
        eventList.add(eventDummy());
        eventList.add(eventDummy());
        eventList.add(eventDummy());
        eventList.add(eventDummy());
        eventList.add(eventDummy());
        eventList.add(eventDummy());
        eventList.add(eventDummy());
        eventList.add(eventDummy());
        eventList.add(eventDummy());



        listEventsIface.fetchedEvents(eventList);
    }

}
