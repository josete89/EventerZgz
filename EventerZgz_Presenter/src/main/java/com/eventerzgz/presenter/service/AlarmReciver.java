package com.eventerzgz.presenter.service;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.eventerzgz.model.event.Event;
import com.eventerzgz.presenter.BasePresenter;
import rx.Subscriber;

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
            AlarmReciver.setAlarm(context);

        }


        BasePresenter.getEventsByPreferencesInOtherThread(context, new Subscriber<List<Event>>() {
            @Override
            public void onCompleted() {
               // AlarmReciver.setAlarm(context);
            }

            @Override
            public void onError(Throwable e) {
                AlarmReciver.setAlarm(context);
            }

            @Override
            public void onNext(List<Event> events) {

                if (events != null && events.size() > 0) {

                    if (events.size() == 1) {
                        Event event = events.get(0);
                        deliverNotification(context, event.getsTitle(), event.getId(), event);

                    } else {
                        deliverNotification(context, " Tienes " + events.size() + " eventos nuevos", events.size() + "", null);
                    }

                }

                onCompleted();
            }
        });





    }


    public void deliverNotification(Context context,String title,String sId,Event event)
    {

        final int IC_LAUNCHER = 0x7f020078;

        PendingIntent contentIntent = null;

        if(event == null){

            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.setComponent(new ComponentName("com.eventerzgz.view","com.eventerzgz.view.activities.ListEventsActivity"));
            contentIntent = PendingIntent.getActivity(context, 0,intent , 0);

        }else{

            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.setComponent(new ComponentName("com.eventerzgz.view","com.eventerzgz.view.activities.DetailEventActivity"));
            intent.putExtra("eventObject",event);
            contentIntent = PendingIntent.getActivity(context, 0,intent , 0);

        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(IC_LAUNCHER)
                        .setContentTitle("EventerZgz")
                        .setContentText(title);

        mBuilder.setContentIntent(contentIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        int id = 5;
        try{
            id = Integer.parseInt(sId);
        }catch (Exception ex){
            ex.printStackTrace();
            id = new Random().nextInt();
        }


        mNotificationManager.notify(id, mBuilder.build());


    }


    public static void setAlarm(Context context)
    {

        boolean alarmUp = (PendingIntent.getBroadcast(context, 1234,
                new Intent(AlarmReciver.class.getName()),
                PendingIntent.FLAG_NO_CREATE) != null);

        if(!alarmUp){

            AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmReciver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 1234, intent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 30);

            //RTC -> Real Time Count
            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, alarmIntent);

      /*  alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() +
                30 * 1000, alarmIntent);*/
        }



    }


}
