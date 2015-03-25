package com.eventerzgz.interactor.test;


import com.eventerzgz.interactor.events.EventInteractor;
import org.junit.Assert;
import org.junit.Test;



/**
 * Created by joseluis on 21/3/15.
 */
public class EventTest {


    @Test
    public void getAllEvents()
    {
        try
        {
            EventInteractor.getAllEvent();
        } catch (Exception e)
        {
            Assert.assertNull(e);
        }
    }


}
