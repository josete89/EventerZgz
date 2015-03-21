package com.eventerzgz.model.commons;

import com.eventerzgz.model.Base;

/**
 * Created by joseluis on 21/3/15.
 */
public class Poblation extends Base
{
    private String sImage;

    public static Poblation doParse(String sRawObj){
        return new Poblation();
    }



    //GETTERS & SETTERS
    public String getsImage() {
        return sImage;
    }

    public void setsImage(String sImage) {
        this.sImage = sImage;
    }

}
