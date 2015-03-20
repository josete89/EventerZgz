package com.eventerzgz.presenter.events;

import java.util.Date;

import android.util.Log;
import com.eventerzgz.interactor.EventInteractor;
import com.eventerzgz.interactor.EventerRepository;
import com.eventerzgz.model.Event;
import com.eventerzgz.presenter.BasePresenter;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by joseluis on 20/3/15.
 */
public class EventsPresenter extends BasePresenter
{
    public final EventsIface eventsIface;

    public EventsPresenter(EventsIface eventsIface)
    {
        this.eventsIface = eventsIface;
    }



    public void createEvent(String sName,String sDescription){

        final Event objEvent = new Event(sName,new Date(),sDescription);

        observerTask(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber suscriber) {
                EventerRepository userRepository = EventInteractor.getInstance();
                try
                {
                    userRepository.createEvent(objEvent);
                    Log.i(TAG, "");
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
                eventsIface.createdEvent();
            }

            @Override
            public void onError(Throwable e) {
                eventsIface.error(e.getMessage());
            }

            @Override
            public void onNext(Boolean o) {

            }
        });


    }


}
