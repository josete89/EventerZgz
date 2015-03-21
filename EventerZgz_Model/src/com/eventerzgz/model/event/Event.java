package com.eventerzgz.model.event;

import com.eventerzgz.model.Base;
import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.commons.ExtraInfo;
import com.eventerzgz.model.commons.Geometry;
import com.eventerzgz.model.commons.Poblation;
import com.eventerzgz.model.exception.EventZgzException;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;
import org.simpleframework.xml.transform.Transform;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by joseluis on 20/3/15.
 */
public class Event extends Base {

    @Element(name="startDate", required = false)
    private Date dStartDate;

    @Element(name="endDate", required = false)
    private Date dEndDate;
    private Date dLastUpdate;

    @Element(name="description")
    private String sDescription;
    private boolean bHighlighted;
    private String sTicketType;
    @Element(name="image", required = false)
    private String sImage;
    private List<Category> categoryList;
    private List<SubEvent> subEventList;
    private List<Poblation> poblationList;
    private List<ExtraInfo> extraInfoList;
    private Geometry objGeometry;


    public static List<Event> doParse(String sRawObj) throws EventZgzException {
        Persister persister = new Persister();
        List<Event> eventList = new ArrayList<>(0);
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            RegistryMatcher m = new RegistryMatcher();
            m.bind(Date.class, new DateFormatTransformer(format));
            persister = new Persister(m);
            SparqlEventList sparqlEventList = persister.read(SparqlEventList.class, sRawObj, false);
            eventList = sparqlEventList.getList();
        } catch (Exception e) {
            throw new  EventZgzException(e);
        }
        return eventList;

    }

    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static void main(String[] list) throws Exception {
        String xml = readFile("C:/temp/eventos.xml", Charset.defaultCharset());
        List<Event> events = doParse(xml);

        System.out.println(events);
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


    public String getImageWithUri() {
        return "http://" + sImage;
    }

    public Date getdStartDate() {
        return dStartDate;
    }

    public void setdStartDate(Date dStartDate) {
        this.dStartDate = dStartDate;
    }
}
