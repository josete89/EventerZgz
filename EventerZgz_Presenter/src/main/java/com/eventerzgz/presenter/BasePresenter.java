package com.eventerzgz.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.CalendarContract;

import android.util.Log;
import com.eventerzgz.model.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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
    private static final String APP_PREFERENCES = "eventerzgz";
    private static final String POBLATION_PREFERENCES_KEY = "poblation";
    private static final String CATEGORIES_PREFERENCES_KEY = "categories";
    private static final String LOCATION_PUSH_PREFERENCES_KEY = "locationPush";

    protected static String TAG = "EventerZgz";

    public void observerTask(Observable.OnSubscribe onSubscribe, Subscriber subscriber)
    {
        Observable.create(onSubscribe).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

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
        String description = event.getsDescription();
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


    public static void saveCategoriesSelectedInPreferences(ArrayList<String> arrayIdsCategories, Context context){
        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Set<String> set = new HashSet<String>();
        set.addAll(arrayIdsCategories);

        editor.putStringSet(CATEGORIES_PREFERENCES_KEY, set);
        editor.commit();

    }

    public static void savePoblationSelectedInPreferences(ArrayList<String> arrayIdsCategories, Context context){
        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Set<String> set = new HashSet<String>();
        set.addAll(arrayIdsCategories);

        editor.putStringSet(POBLATION_PREFERENCES_KEY, set);
        editor.commit();

    }

    public static List<String> getPoblation(Context context){

        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Set stringSet = prefs.getStringSet(POBLATION_PREFERENCES_KEY,new HashSet<String>());

        return new ArrayList<>(stringSet);
    }

    public static List<String> getCategories(Context context){

        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Set stringSet = prefs.getStringSet(CATEGORIES_PREFERENCES_KEY,new HashSet<String>());

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
        editor.commit();
    }

    public static String getLocationFromPreferences(Context context){

        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        return prefs.getString(LOCATION_PUSH_PREFERENCES_KEY, "");
    }

}
