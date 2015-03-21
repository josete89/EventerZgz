package com.eventerzgz.model.commons;

/**
 * Created by joseluis on 21/3/15.
 */
public class Geometry
{

    private String sType;
    private Coordinates objCoordinates;

    public static Geometry doParse(String sRawObj){
        return new Geometry();
    }


    //GETTERS & SETTERS
    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }

    public Coordinates getObjCoordinates() {
        return objCoordinates;
    }

    public void setObjCoordinates(Coordinates objCoordinates) {
        this.objCoordinates = objCoordinates;
    }
}
