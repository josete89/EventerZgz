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

    public static List<Event> getAllEvent(EventFilter... eventFilter) throws EventZgzException
    {

        //Decidir la impl

        return new EventerRest().getAllEvents(eventFilter);

    }

    public void findById(String sId){

    }



}
