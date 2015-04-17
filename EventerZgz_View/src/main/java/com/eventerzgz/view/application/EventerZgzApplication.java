package com.eventerzgz.view.application;

/**
 * Created by JavierArroyo on 21/3/15.
 */

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.commons.Population;
import com.eventerzgz.presenter.service.AlarmReciver;
import com.eventerzgz.view.R;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.List;


/**
 * Created by JavierArroyo on 21/3/15.
 */
public class EventerZgzApplication  extends Application {

    //Data intent
    //-----------
    public static final String INTENT_EVENT_SELECTED = "eventObject";

    //DATA
    //----
    public static List<Category> categoryList;
    public static List<Population> populationList;


    public void startService(){
        // TODO - Comprobar que no este lanzado ya
        AlarmReciver.setAlarm(getApplicationContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Crashlytics.start(this);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.imagen_cabecera) // resource or drawable
                .showImageOnFail(R.drawable.imagen_cabecera) // resource or drawable
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(1000)
                .cacheInMemory(true) // default
                .cacheOnDisk(true)

                .build();
        // Se comenta parte del mtodo para evitar que saque log
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(options)
                .memoryCache(new UsingFreqLimitedMemoryCache(2000000)).memoryCacheSize(1500000).threadPoolSize(3)
                .memoryCache(new WeakMemoryCache())/*
                                                         * .enableLogging ()
                                                         */
                .build();

        // Iniciar ImageLoader
        ImageLoader.getInstance().init(config);

        startService();
    }



}
