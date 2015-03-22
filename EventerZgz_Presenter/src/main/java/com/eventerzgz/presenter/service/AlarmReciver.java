package com.eventerzgz.presenter.service;

import static com.eventerzgz.interactor.events.EventInteractor.EventFilter;

import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.eventerzgz.interactor.QueryBuilder;
import com.eventerzgz.interactor.events.EventInteractor;
import com.eventerzgz.model.event.Event;
import com.eventerzgz.presenter.BasePresenter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by joseluis on 21/3/15.
 */
public class AlarmReciver  extends BroadcastReceiver{

    private final String TAG = "AlarmReciver";
    private static AlarmIface alarmIface;

    @Override
    public void onReceive(final Context context, Intent intent)
    {
        //context.startService(new Intent(context, EventService.class));

        if (intent != null && intent.getAction() != null && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
            AlarmReciver.setAlarm(context,alarmIface);

        }


        Observable.create(new Observable.OnSubscribe<List<Event>>() {
            @Override
            public void call(Subscriber<? super List<Event>> subscriber) {

                try
                {
                    String location = BasePresenter.getLocationFromPreferences(context);
                    List<String> categories = BasePresenter.getCategories(context);
                    List<String> poblations = BasePresenter.getPoblation(context);

                    QueryBuilder query = new QueryBuilder();
                    for ( String category : categories){
                        query.addFilter(QueryBuilder.FIELD.CATEGORY, QueryBuilder.COMPARATOR.EQUALS,category);
                    }
                    query.addFilter(QueryBuilder.FIELD.LAST_UPDATED,QueryBuilder.COMPARATOR.GREATER_EQUALS,"2015-03-10T00:00:00Z");


                    subscriber.onNext(EventInteractor.getAllEvent(
                            EventFilter.createFilter(EventFilter.QUERY_FILTER, query.build()),
                            EventFilter.createFilter(EventFilter.START, 0),
                            EventFilter.createFilter(EventFilter.SORT, "startDate desc"), // "desc" is optional
                            EventFilter.createFilter(EventFilter.ROWS, 50),
                            EventFilter.createFilter(EventFilter.DISTANCE, 3000), //metros
                            EventFilter.createFilter(EventFilter.POINT, location)));
                }
                catch (Exception ex){
                    Log.e(TAG,ex.getMessage(),ex);
                    subscriber.onError(ex);
                }

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Event>>() {
                    @Override
                    public void onCompleted() {
                        AlarmReciver.setAlarm(context,alarmIface);
                    }

                    @Override
                    public void onError(Throwable e) {
                        AlarmReciver.setAlarm(context,alarmIface);
                    }

                    @Override
                    public void onNext(List<Event> events) {

                        if (events != null && events.size() > 0) {

                            if (events.size() == 1) {
                                Event event = events.get(0);
                               if(alarmIface != null) alarmIface.deliverNotification(context, event.getsTitle(), event.getId(), event);

                            } else {
                                if(alarmIface != null) alarmIface.deliverNotification(context, " Tienes " + events.size() + " eventos nuevos", events.size() + "",null);
                            }

                        }

                        onCompleted();
                    }
                });




    }

    public static void setAlarm(Context context,AlarmIface alarmIface)
    {

        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReciver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() +
                6 * 1000, alarmIntent);
    }


}
