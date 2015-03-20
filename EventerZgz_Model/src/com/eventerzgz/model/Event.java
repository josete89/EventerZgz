package com.eventerzgz.model;

import java.util.Date;

/**
 * Created by joseluis on 20/3/15.
 */
public class Event extends Base
{
    private String sTitle;
    private Date   dDate;
    private String sDescription;

    public Event(String sTitle, Date dDate, String sDescription) {
        this.sTitle = sTitle;
        this.dDate = dDate;
        this.sDescription = sDescription;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
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
