package com.eventerzgz.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.CalendarContract;
import android.util.Log;

import com.eventerzgz.interactor.QueryBuilder;
import com.eventerzgz.interactor.events.EventInteractor;
import com.eventerzgz.interactor.population.PopulationInteractor;
import com.eventerzgz.model.commons.Population;
import com.eventerzgz.model.event.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by joseluis on 20/3/15.
 */
public abstract class BasePresenter
{
    //Preferences
    //-----
    private static final String APP_PREFERENCES                 = "eventerzgz";
    private static final String POBLATION_PREFERENCES_KEY       = "poblation";
    private static final String TUTORIAL_MADE_KEY               = "tutorialMade";
    private static final String CATEGORIES_PREFERENCES_KEY      = "categories";
    private static final String LOCATION_PUSH_PREFERENCES_KEY   = "locationPush";

    protected static String TAG = "EventerZgz";

    public <T> void observerTask(Observable.OnSubscribe<T> onSubscribe, Subscriber<T> subscriber)
    {
        Observable.create(onSubscribe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void getPopulationinOtherThread(Subscriber<List<Population>> subscriber){
        observerTask(new Observable.OnSubscribe<List<Population>>() {
            @Override
            public void call(Subscriber<? super List<Population>> suscriber) {
                try {
                    suscriber.onNext(PopulationInteractor.getPopulations());
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                    suscriber.onError(e);
                }
            }
        }, subscriber);
    }

    public void addCalendarEvent(Event event,Context ctx)
    {

        Calendar beginTime = Calendar.getInstance();
        if(event.getdStartDate()!=null) {
            beginTime.setTime(event.getdStartDate());
        }
        if(event.getdEndDate()!=null){
            beginTime.setTime(event.getdEndDate());
        }
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(event.getdEndDate());

        String title = event.getsTitle();
        String location = (event.getSubEvent() != null && event.getSubEvent().getWhere() != null) ? event.getSubEvent().getWhere().getsAddress():"";
        String email = (event.getSubEvent() != null && event.getSubEvent().getWhere() != null) ? event.getSubEvent().getWhere().getsMail():"";

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_FREE)
                .putExtra(Intent.EXTRA_EMAIL, email);

        ctx.startActivity(intent);
    }

    public static void saveTutorialMade( Context context){
        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean(TUTORIAL_MADE_KEY, true);
        editor.apply();

    }

    public static boolean getTutorialMade( Context context){
        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        return prefs.getBoolean(TUTORIAL_MADE_KEY, false);

    }


    public static void saveCategoriesSelectedInPreferences(ArrayList<String> arrayIdsCategories, Context context){
        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Set<String> set = new HashSet<>();
        set.addAll(arrayIdsCategories);

        editor.putStringSet(CATEGORIES_PREFERENCES_KEY, set);
        editor.apply();

    }

    public static void savePoblationSelectedInPreferences(ArrayList<String> arrayIdsCategories, Context context){
        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Set<String> set = new HashSet<>();
        set.addAll(arrayIdsCategories);

        editor.putStringSet(POBLATION_PREFERENCES_KEY, set);
        editor.apply();

    }

    public static List<String> getPoblation(Context context)
    {

        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Set<String> stringSet = prefs.getStringSet(POBLATION_PREFERENCES_KEY, new HashSet<String>());

        return new ArrayList<>(stringSet);
    }

    public static List<String> getCategories(Context context)
    {

        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Set<String> stringSet = prefs.getStringSet(CATEGORIES_PREFERENCES_KEY, new HashSet<String>());

        return new ArrayList<>(stringSet);
    }

    public static void saveLocationPushInPreferences(Double latitude,Double longuitude,Context context){
        if(latitude == null || longuitude == null){
            Log.e(TAG,"latitude or longuite null");
            return;
        }

        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(LOCATION_PUSH_PREFERENCES_KEY, String.format(Locale.ENGLISH,"%f,%f",longuitude,latitude));
        editor.apply();
    }

    public static String getLocationFromPreferences(Context context){

        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        return prefs.getString(LOCATION_PUSH_PREFERENCES_KEY, "");
    }



    public static void getEventsByPreferencesInOtherThread (final Context context,Subscriber<List<Event>> subscriber){

        Observable.create(new Observable.OnSubscribe<List<Event>>() {
            @Override
            public void call(Subscriber<? super List<Event>> subscriber) {

                try
                {
                    String location = BasePresenter.getLocationFromPreferences(context);
                    List<String> categoryIds = BasePresenter.getCategories(context);
                    List<String> poblations = BasePresenter.getPoblation(context);

                    QueryBuilder queryBuilder = new QueryBuilder().fromToday();

                    composeQueryLIst(queryBuilder,categoryIds, QueryBuilder.FIELD.CATEGORY);
                    composeQueryLIst(queryBuilder, poblations, QueryBuilder.FIELD.POPULATION);


                    queryBuilder.and().addFilter(QueryBuilder.FIELD.LAST_UPDATED, QueryBuilder.COMPARATOR.GREATER_EQUALS, "2015-03-10T00:00:00Z");

                    if(location.length() > 0){
                        subscriber.onNext(EventInteractor.getAllEvent(
                                EventInteractor.EventFilter.createFilter(EventInteractor.EventFilter.QUERY_FILTER, queryBuilder.build()),
                                EventInteractor.EventFilter.createFilter(EventInteractor.EventFilter.START, 0),
                                EventInteractor.EventFilter.createFilter(EventInteractor.EventFilter.SORT, QueryBuilder.FIELD.END_DATE + " desc"), // "desc" is optional
                                EventInteractor.EventFilter.createFilter(EventInteractor.EventFilter.ROWS, 50),
                                EventInteractor.EventFilter.createFilter(EventInteractor.EventFilter.DISTANCE, 3000), //metros
                                EventInteractor.EventFilter.createFilter(EventInteractor.EventFilter.POINT, location)));
                    }else{
                        subscriber.onNext(EventInteractor.getAllEvent(
                                EventInteractor.EventFilter.createFilter(EventInteractor.EventFilter.QUERY_FILTER, queryBuilder.build()),
                                EventInteractor.EventFilter.createFilter(EventInteractor.EventFilter.START, 0),
                                EventInteractor.EventFilter.createFilter(EventInteractor.EventFilter.SORT, QueryBuilder.FIELD.END_DATE + " desc" ), // "desc" is optional
                                EventInteractor.EventFilter.createFilter(EventInteractor.EventFilter.ROWS, 50)));
                    }


                }
                catch (Exception ex){
                    Log.e(TAG,ex.getMessage(),ex);
                    subscriber.onError(ex);
                }

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    protected static QueryBuilder composeQueryLIst(QueryBuilder queryBuilder,List<String> list,QueryBuilder.FIELD field){

        if (list != null && list.size() > 0) {
            boolean first = true;
            queryBuilder.and().group();
            for (String categoryId : list) {
                if (!first) {
                    queryBuilder.or();
                } else {
                    first = false;
                }
                queryBuilder.addFilter(field, QueryBuilder.COMPARATOR.EQUALS, categoryId);
            }
            queryBuilder.ungroup();
        }

        return queryBuilder;
    }

    /**
     * Translate list Events into
     * @param eventList List of events
     * @return Map by dates
     */
    protected Map<Date,List<Event>> populateMapFromListEvents(List<Event> eventList)
    {
        Map<Date,List<Event>> dateListMap = new HashMap<>();
        List<Event> listAux;
        Date key;
        for(Event event:eventList)
        {

            key = event.getdEndDate();

            if(dateListMap.containsKey(key)){

                listAux = dateListMap.get(key);
                listAux.add(event);

            }else{
                listAux = Collections.emptyList();
                listAux.add(event);

                dateListMap.put(key, listAux);
            }
        }


        return dateListMap;
    }

    protected List<Event> filterListByDateBeforeToday(List<Event>eventList){

        List<Event> events = new ArrayList<>(0);

        if ( eventList == null || eventList.size() == 0)
            return  eventList;

        for (Event event:eventList){
            if(!isBeforeToday(event.getdEndDate(),event)){
                events.add(event);
            }
        }


        return events;

    }

    private boolean isBeforeToday(Date dateToCheck,Event event) {

        //Log.i(TAG,"Date To evaluate ->"+dateToCheck.toString());

        if (dateToCheck == null) {
            Log.e(TAG, "NULL date for event" + event.toString());
            return true;
        }

        Calendar c = Calendar.getInstance();

        // set the calendar to start of today
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);


        Calendar c2 = Calendar.getInstance();
        c2.setTime(dateToCheck);


        return  c.get(Calendar.ERA) < c2.get(Calendar.ERA) ||
                c.get(Calendar.ERA) > c2.get(Calendar.ERA) ||
                c.get(Calendar.YEAR) < c2.get(Calendar.YEAR) ||
                c.get(Calendar.YEAR) > c2.get(Calendar.YEAR) ||
                c.get(Calendar.DAY_OF_YEAR) > c2.get(Calendar.DAY_OF_YEAR);
    }



}
