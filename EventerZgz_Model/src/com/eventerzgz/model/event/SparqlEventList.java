package com.eventerzgz.model.event;

import org.simpleframework.xml.ElementList;

import java.util.List;

/**
 * Created by Omar on 21/03/2015.
 */
public class SparqlEventList {
    @ElementList
    private List<Event> result;

    public List<Event> getList() {
        return result;
    }
}

