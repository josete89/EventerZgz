package com.eventerzgz.model;

/**
 * Created by joseluis on 20/3/15.
 */
public abstract class Base
{
    private int id;
    private String sTitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }


    public String getFieldWithUri(String sFieldValue){
        return "http:"+sFieldValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Base)) return false;

        Base base = (Base) o;

        if (id != base.id) return false;
        if (sTitle != null ? !sTitle.equals(base.sTitle) : base.sTitle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (sTitle != null ? sTitle.hashCode() : 0);
        return result;
    }
}
