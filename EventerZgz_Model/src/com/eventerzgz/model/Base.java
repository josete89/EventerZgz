package com.eventerzgz.model;

/**
 * Created by joseluis on 20/3/15.
 */
public abstract class Base
{
    private Integer id;
    private String sTitle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }
}
