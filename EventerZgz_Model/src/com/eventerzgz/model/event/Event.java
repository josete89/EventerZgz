package com.eventerzgz.model.event;

import com.eventerzgz.model.Base;
import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.commons.ExtraInfo;
import com.eventerzgz.model.commons.Geometry;
import com.eventerzgz.model.commons.Poblation;

import java.util.Date;
import java.util.List;

/**
 * Created by joseluis on 20/3/15.
 */
public class Event extends Base
{
    private Date   dEndDate;
    private Date   dLastUpdate;
    private String sDescription;
    private boolean bHighlighted;
    private String sTicketType;
    private String sImage;
    private List<Category> categoryList;
    private List<SubEvent> subEventList;
    private List<Poblation> poblationList;
    private List<ExtraInfo> extraInfoList;
    private Geometry    objGeometry;


    public static Event doParse(String sRawObj){
        return new Event();
    }



    //GETTERS & SETTERS
    public Date getdEndDate() {
        return dEndDate;
    }

    public void setdEndDate(Date dEndDate) {
        this.dEndDate = dEndDate;
    }

    public Date getdLastUpdate() {
        return dLastUpdate;
    }

    public void setdLastUpdate(Date dLastUpdate) {
        this.dLastUpdate = dLastUpdate;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    public boolean isbHighlighted() {
        return bHighlighted;
    }

    public void setbHighlighted(boolean bHighlighted) {
        this.bHighlighted = bHighlighted;
    }

    public String getsTicketType() {
        return sTicketType;
    }

    public void setsTicketType(String sTicketType) {
        this.sTicketType = sTicketType;
    }

    public String getsImage() {
        return sImage;
    }

    public void setsImage(String sImage) {
        this.sImage = sImage;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<SubEvent> getSubEventList() {
        return subEventList;
    }

    public void setSubEventList(List<SubEvent> subEventList) {
        this.subEventList = subEventList;
    }

    public List<Poblation> getPoblationList() {
        return poblationList;
    }

    public void setPoblationList(List<Poblation> poblationList) {
        this.poblationList = poblationList;
    }

    public List<ExtraInfo> getExtraInfoList() {
        return extraInfoList;
    }

    public void setExtraInfoList(List<ExtraInfo> extraInfoList) {
        this.extraInfoList = extraInfoList;
    }

    public Geometry getObjGeometry() {
        return objGeometry;
    }

    public void setObjGeometry(Geometry objGeometry) {
        this.objGeometry = objGeometry;
    }
}
