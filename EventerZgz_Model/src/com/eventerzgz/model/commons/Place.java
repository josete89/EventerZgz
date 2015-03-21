package com.eventerzgz.model.commons;

/**
 * Created by joseluis on 21/3/15.
 */
public class Place
{
    private String sId;
    private String sTitle;
    private String sAddress;
    private String sCP;
    private String sTown;
    private String sProvince;
    private String sCountry;
    private String sTelephone;
    private String sFax;
    private String sMail;
    private String sBus;
    private String sAccessibility;
    private Coordinates objCoordinates;

    public static Place doParse(String sRawObj){
        return new Place();
    }


    //GETTERS & SETTERS
    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getsCP() {
        return sCP;
    }

    public void setsCP(String sCP) {
        this.sCP = sCP;
    }

    public String getsTown() {
        return sTown;
    }

    public void setsTown(String sTown) {
        this.sTown = sTown;
    }

    public String getsProvince() {
        return sProvince;
    }

    public void setsProvince(String sProvince) {
        this.sProvince = sProvince;
    }

    public String getsCountry() {
        return sCountry;
    }

    public void setsCountry(String sCountry) {
        this.sCountry = sCountry;
    }

    public String getsTelephone() {
        return sTelephone;
    }

    public void setsTelephone(String sTelephone) {
        this.sTelephone = sTelephone;
    }

    public String getsFax() {
        return sFax;
    }

    public void setsFax(String sFax) {
        this.sFax = sFax;
    }

    public String getsMail() {
        return sMail;
    }

    public void setsMail(String sMail) {
        this.sMail = sMail;
    }

    public String getsBus() {
        return sBus;
    }

    public void setsBus(String sBus) {
        this.sBus = sBus;
    }

    public String getsAccessibility() {
        return sAccessibility;
    }

    public void setsAccessibility(String sAccessibility) {
        this.sAccessibility = sAccessibility;
    }

    public Coordinates getObjCoordinates() {
        return objCoordinates;
    }

    public void setObjCoordinates(Coordinates objCoordinates) {
        this.objCoordinates = objCoordinates;
    }


}
