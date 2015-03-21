package com.eventerzgz.model.event;

import java.util.Date;

import com.eventerzgz.model.Base;

/**
 * Created by joseluis on 20/3/15.
 */
public class Event extends Base
{
    private Date   dDate;
    private String sDescription;

    public Event(String sTitle, Date dDate, String sDescription)
    {
        setsTitle(sTitle);
        this.dDate = dDate;
        this.sDescription = sDescription;
    }

    public Date getdDate() {
        return dDate;
    }

    public void setdDate(Date dDate) {
        this.dDate = dDate;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }
}
