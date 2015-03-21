package com.eventerzgz.model.event;

import com.eventerzgz.model.commons.Place;

/**
 * Created by joseluis on 21/3/15.
 */
public class SubEvent {

    private Place objPlace;
    private String sEndDate;

    public static Event doParse(String sRawObj){
        return new Event();
    }

    //GETTERS & SETTERS

    public Place getObjPlace() {
        return objPlace;
    }

    public void setObjPlace(Place objPlace) {
        this.objPlace = objPlace;
    }

    public String getsEndDate() {
        return sEndDate;
    }

    public void setsEndDate(String sEndDate) {
        this.sEndDate = sEndDate;
    }
}
