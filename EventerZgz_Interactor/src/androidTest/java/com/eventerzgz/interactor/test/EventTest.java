package com.eventerzgz.interactor.test;

import com.eventerzgz.interactor.events.EventInteractor;
import com.eventerzgz.model.exception.EventZgzException;
import org.junit.Test;


/**
 * Created by joseluis on 21/3/15.
 */
public class EventTest {

    @Test
    public void test(){
        try {
            EventInteractor.getAllEvent();
        } catch (EventZgzException e) {
            e.printStackTrace();
        }
    }


}
