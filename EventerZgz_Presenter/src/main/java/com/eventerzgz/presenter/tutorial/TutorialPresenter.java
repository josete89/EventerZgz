package com.eventerzgz.presenter.tutorial;

import java.util.List;

import android.util.Log;
import com.eventerzgz.interactor.category.CategoryInteractor;
import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.commons.Population;
import com.eventerzgz.presenter.BasePresenter;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by joseluis on 21/3/15.
 */
public class TutorialPresenter extends BasePresenter
{

    public final TutorialIface tutorialIface;

    public TutorialPresenter(TutorialIface tutorialIface) {
        this.tutorialIface = tutorialIface;
    }

    public void getCategories(){

        observerTask(new Observable.OnSubscribe<List<Category>>() {
            @Override
            public void call(Subscriber<? super List<Category>> suscriber) {
                try
                {
                    suscriber.onNext(CategoryInteractor.getCategories());
                } catch (Exception e)
                {
                    Log.e(TAG, e.getMessage(), e);
                    suscriber.onError(e);
                }
            }
        }, new Subscriber<List<Category>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                tutorialIface.error(e.getMessage());
            }

            @Override
            public void onNext(List<Category> o) {
                tutorialIface.fechedCategories(o);
                onCompleted();
            }
        });
    }


    public void getPopulation(){

        getPopulationinOtherThread(new Subscriber<List<Population>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                tutorialIface.error(e.getMessage());
            }

            @Override
            public void onNext(List<Population> o) {
                tutorialIface.fechedPopulation(o);
                onCompleted();
            }
        });
    }



}
