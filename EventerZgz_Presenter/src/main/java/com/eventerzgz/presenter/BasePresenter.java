package com.eventerzgz.presenter;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;

import com.eventerzgz.model.event.Event;

import java.util.Calendar;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by joseluis on 20/3/15.
 */
public abstract class BasePresenter
{

    protected String TAG = "EventerZgz";

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

}
