package com.eventerzgz.model.commons;

/**
 * Created by joseluis on 21/3/15.
 */
public class Coordinates
{
    private float fLatitude;
    private float fLonguide;

    public static Coordinates doParse(String sRawObj){
        return new Coordinates();
    }


    //GETTERS & SETTERS
    public float getfLatitude() {
        return fLatitude;
    }

    public void setfLatitude(float fLatitude) {
        this.fLatitude = fLatitude;
    }

    public float getfLonguide() {
        return fLonguide;
    }

    public void setfLonguide(float fLonguide) {
        this.fLonguide = fLonguide;
    }

}
