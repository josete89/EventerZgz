package com.eventerzgz.interactor.events;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.eventerzgz.interactor.BaseInteractor;
import com.eventerzgz.model.event.Event;
import com.eventerzgz.model.exception.EventZgzException;


/**
 * Created by joseluis on 20/3/15.
 */
public class EventInteractor extends BaseInteractor{

    public static enum EventFilter{

        CATEGORY_FILTER("title") {
            @Override
            public void appendParam(StringBuilder stringBuilder) {

                String s = "";

                try {
                    s = URLEncoder.encode(getFilterValue(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                stringBuilder.append("&").append(getKeyValue()).append("==").append(s);
            }
        },
        START (""){
            @Override
            public void appendParam(StringBuilder stringBuilder) {
                String s = "";

                try {
                    s = URLEncoder.encode(getFilterValue(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                stringBuilder.append("&").append(getKeyValue()).append("==").append(s);
            }
        },
        ROWS (""){
            @Override
            public void appendParam(StringBuilder stringBuilder) {
                String s = "";

                try {
                    s = URLEncoder.encode(getFilterValue(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                stringBuilder.append("&").append(getKeyValue()).append("==").append(s);
            }
        },
        DISTANCE (""){
            @Override
            public void appendParam(StringBuilder stringBuilder) {
                String s = "";

                try {
                    s = URLEncoder.encode(getFilterValue(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                stringBuilder.append("&").append(getKeyValue()).append("==").append(s);
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

        public abstract void appendParam(StringBuilder stringBuilder);

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
