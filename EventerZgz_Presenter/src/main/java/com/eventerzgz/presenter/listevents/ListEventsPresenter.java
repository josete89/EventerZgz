package com.eventerzgz.presenter.listevents;

import android.util.Log;

import com.eventerzgz.model.event.Event;
import com.eventerzgz.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

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

        final List<Event> eventList = new ArrayList<>();
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





        observerTask(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber suscriber) {
                try
                {
                   // EventInteractor.getInstance().getAllEvent(null);
                    Log.i(TAG, "Event created!!");

                    suscriber.onCompleted();
                } catch (Exception e)
                {
                    Log.e(TAG, e.getMessage(), e);
                    suscriber.onError(e);
                }
            }
        }, new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                listEventsIface.fetchedEvents(eventList);
            }

            @Override
            public void onError(Throwable e) {
                listEventsIface.error(e.getMessage());
            }

            @Override
            public void onNext(Boolean o) {

            }
        });
    }

}
