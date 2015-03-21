package com.eventerzgz.presenter;

import java.util.Calendar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.CalendarContract;
import com.eventerzgz.model.event.Event;
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
        beginTime.setTime(event.getdStartDate());
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(event.getdEndDate());

        String title = event.getsTitle();
        String description = event.getsDescription();
        String location = event.getSubEventList() != null && event.getSubEventList().size() > 0 ? event.getSubEventList().get(0).getObjPlace().getsAddress():"";
        String email = event.getSubEventList() != null && event.getSubEventList().size() > 0 ? event.getSubEventList().get(0).getObjPlace().getsMail():"";

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.DESCRIPTION, description)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_FREE)
                .putExtra(Intent.EXTRA_EMAIL, email);

        ctx.startActivity(intent);
    }

}
