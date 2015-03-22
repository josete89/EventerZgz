package com.eventerzgz.view.application;

/**
 * Created by JavierArroyo on 21/3/15.
 */

import android.app.Application;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.event.Event;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.List;

/**
 * Created by JavierArroyo on 21/3/15.
 */
public class EventerZgzApplication  extends Application {

    //Data intent
    //-----------
    public static final String INTENT_EVENT_SELECTED = "posEventSelected";
    public static final String INTENT_EVENT_FILTERED = "eventFiltered";

    //Data
    //----
    public static List<Event> allEventsList;
    public static List<Event> filterEventsList;
    public static List<Category> categoryList;

    //Preferences
    //-----
    public static final String APP_PREFERENCES = "eventerzgz";

    @Override
    public void onCreate() {
        super.onCreate();
        // Se comenta parte del mtodo para evitar que saque log
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)

                .memoryCache(new UsingFreqLimitedMemoryCache(2000000)).memoryCacheSize(1500000).threadPoolSize(3)
                .memoryCache(new WeakMemoryCache())/*
                                                         * .enableLogging ()
                                                         */
                .build();

        // Iniciar ImageLoader
        ImageLoader.getInstance().init(config);
        JodaTimeAndroid.init(this);
    }
}
