package com.eventerzgz.interactor.events;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import android.util.Log;
import com.eventerzgz.interactor.BaseInteractor;
import com.eventerzgz.model.event.Event;
import com.eventerzgz.model.exception.EventZgzException;


/**
 * Created by joseluis on 20/3/15.
 */
public class EventInteractor extends BaseInteractor{

    public static enum EventFilter{

        CATEGORY_FILTER("") {
            @Override
            public void fillLinkedMap(LinkedHashMap linkedMap) {
                linkedMap.put(getKeyValue(),getFilterValue());
            }
        },
        START (""){
            @Override
            public void fillLinkedMap(LinkedHashMap linkedMap) {
                linkedMap.put(getKeyValue(),getFilterValue());
            }
        },
        ROWS (""){
            @Override
            public void fillLinkedMap(LinkedHashMap linkedMap) {
                linkedMap.put(getKeyValue(),getFilterValue());
            }
        },
        DISTANCE (""){
            @Override
            public void fillLinkedMap(LinkedHashMap linkedMap) {
                linkedMap.put(getKeyValue(),getFilterValue());
            }
        };

        private String keyValue;
        private String filterValue;

        EventFilter(String s) {
            this.keyValue = s;
        }


        public static EventFilter createEnumWithValue(EventFilter eventFilter,String sValue){
            eventFilter.setFilterValue(sValue);
            return eventFilter;
        }

        public abstract void fillLinkedMap(LinkedHashMap linkedMap);

        public String getKeyValue() {
            return keyValue;
        }

        private void setFilterValue(String filterValue) {
            this.filterValue = filterValue;
        }

        public String getFilterValue() {
            return filterValue;
        }


    }

    public void createEvent(Event objEvent)
    {

    }

    public void updateEvent(Event objEvent)
    {

    }

    public void deleteEvent(String sId)
    {

    }

    public List<Event> getAllEvent(EventFilter... eventFilter) throws EventZgzException
    {
        List<Event> eventList = new ArrayList<>();

        String sUrl = "http://www.zaragoza.es/api/recurso/cultura-ocio/evento-zaragoza.xml?q=title==Taller*";

        String content = doHTTPGet(sUrl);

        Log.i(TAG,content);

        return eventList;
    }

    public void findById(String sId){

    }


    public static EventInteractor getInstance()
    {
        return new EventInteractor();
    }
}
