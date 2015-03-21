package com.eventerzgz.presenter.service;

import java.util.List;
import java.util.Random;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import com.eventerzgz.interactor.events.EventInteractor;
import com.eventerzgz.model.event.Event;
import com.eventerzgz.model.exception.EventZgzException;

/**
 * Created by joseluis on 21/3/15.
 */
public class AlarmReciver  extends BroadcastReceiver{
    @Override
    public void onReceive(final Context context, Intent intent) {
        //context.startService(new Intent(context, EventService.class));

        if (intent != null && intent.getAction() != null && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.

            AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent2 = new Intent(context, AlarmReciver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent2, 0);

            alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() +
                    6 * 1000, alarmIntent);
        }

        try
        {


            final List<Event> eventList = EventInteractor.getAllEvent();

            if( eventList != null && eventList.size() > 0 )
            {
                if(eventList.size() == 1)
                {

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Event event = eventList.get(0);
                            composeNotification(context,event.getsTitle(),event.getId());
                        }
                    });

                }
                else
                {
                    final Event event = eventList.get(0);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            composeNotification(context," Tienes "+eventList.size()+" eventos nuevos",eventList.size()+"");
                        }
                    });

                }

            }



        } catch (EventZgzException e)
        {
            e.printStackTrace();
        }

    }

    public void composeNotification(Context context,String title,String sId){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(android.R.drawable.ic_notification_overlay)
                        .setContentTitle("EventerZgz")
                        .setContentText(title);


        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.

        int id = 5;
        try{
            Integer.parseInt(sId);
        }catch (Exception ex){
            ex.printStackTrace();
            id = new Random().nextInt();
        }


        mNotificationManager.notify(id, mBuilder.build());
    }
}
