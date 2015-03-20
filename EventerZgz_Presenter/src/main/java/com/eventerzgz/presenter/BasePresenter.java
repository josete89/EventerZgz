package com.eventerzgz.presenter;

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

    public void observerTask(Observable.OnSubscribe onSubscribe, Subscriber subscriber){
        Observable.create(onSubscribe).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

}
