package com.eventerzgz.presenter.listevents;

import android.util.Log;
import com.eventerzgz.interactor.category.CategoryInteractor;
import com.eventerzgz.interactor.events.EventInteractor;
import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.event.Event;
import com.eventerzgz.presenter.BasePresenter;
import rx.Observable;
import rx.Subscriber;

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

        observerTask(new Observable.OnSubscribe<List<Event>>() {
            @Override
            public void call(Subscriber suscriber) {
                try
                {
                    suscriber.onNext(EventInteractor.getAllEvent());
                } catch (Exception e)
                {
                    Log.e(TAG, e.getMessage(), e);
                    suscriber.onError(e);
                }
            }
        }, new Subscriber<List<Event>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listEventsIface.error(e.getMessage());
            }

            @Override
            public void onNext(List<Event> o) {
                listEventsIface.fetchedEvents(o);
            }
        });

        observerTask(new Observable.OnSubscribe<List<Category>>() {
            @Override
            public void call(Subscriber suscriber) {
                try
                {
                    suscriber.onNext(CategoryInteractor.getCategories());
                } catch (Exception e)
                {
                    Log.e(TAG, e.getMessage(), e);
                    suscriber.onError(e);
                }
            }
        }, new Subscriber<List<Category>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listEventsIface.error(e.getMessage());
            }

            @Override
            public void onNext(List<Category> o) {
                listEventsIface.fetchedCategories(o);
                onCompleted();
            }
        });

    }

}
