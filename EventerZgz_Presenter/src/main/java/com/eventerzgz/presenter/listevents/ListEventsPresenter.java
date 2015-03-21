package com.eventerzgz.presenter.listevents;

import android.util.Log;

import com.eventerzgz.interactor.QueryBuilder;
import com.eventerzgz.interactor.category.CategoryInteractor;
import com.eventerzgz.interactor.events.EventInteractor;
import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.event.Event;
import com.eventerzgz.presenter.BasePresenter;

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

    public static Event eventDummy() {
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
                try {
                    String query = new QueryBuilder()
                            .addFilter("startDate", QueryBuilder.COMPARATOR.GREATER_EQUALS, "2015-03-01T00:00:00Z")
                            .addFilter("endDate", QueryBuilder.COMPARATOR.GREATER_EQUALS, "2015-03-01T00:00:00Z")
                            .build();
                    suscriber.onNext(EventInteractor.getAllEvent(
                            EventInteractor.EventFilter.createFilter(EventInteractor.EventFilter.QUERY_FILTER, query),
                            EventInteractor.EventFilter.createFilter(EventInteractor.EventFilter.START, 0),
                            EventInteractor.EventFilter.createFilter(EventInteractor.EventFilter.SORT, "startDate desc"), // "desc" is optional
                            EventInteractor.EventFilter.createFilter(EventInteractor.EventFilter.ROWS, 50),
                            EventInteractor.EventFilter.createFilter(EventInteractor.EventFilter.DISTANCE, 3000), //metros
                            EventInteractor.EventFilter.createFilter(EventInteractor.EventFilter.POINT, "-0.8830288063687367,41.62968403793101") // va de la mano de DISTANCE
                    ));
                } catch (Exception e) {
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
                onCompleted();
            }
        });

        observerTask(new Observable.OnSubscribe<List<Category>>() {
            @Override
            public void call(Subscriber suscriber) {
                try {
                    suscriber.onNext(CategoryInteractor.getCategories());
                } catch (Exception e) {
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
