package com.eventerzgz.interactor;

import android.util.Log;

import com.eventerzgz.model.exception.EventZgzException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by joseluis on 21/3/15.
 */
public class QueryBuilder {

    private StringBuffer query = new StringBuffer();

    public enum COMPARATOR{
        EQUALS("=="),
        LESSER_EQUALS("=le="),
        LESSER("=lt="),
        GREATER_EQUALS("=le="),
        GREATER("=lt="),
        NOT_EQUALS("!=");

        private final String value;

        private COMPARATOR(final String newValue) {
            value = newValue;
        }

        public String toString() { return value; }
    }

    public enum FIELD{
        START_DATE("startDate"),
        END_DATE("endDate"),
        CATEGORY("temas.id");

        private final String value;

        private FIELD(final String newValue) {
            value = newValue;
        }

        public String toString() { return value; }
    }

    public QueryBuilder addFilter(FIELD key, COMPARATOR comparator, String value) {
        if (query.length() > 0) {
            query.append(';');
        }
        query.append(key).append(comparator).append(value);
        return this;
    }

    public String build() {
        return query.toString();
    }

}
