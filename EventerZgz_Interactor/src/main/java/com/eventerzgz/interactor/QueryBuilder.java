package com.eventerzgz.interactor;

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
        LAST_UPDATED("lastUpdated"),
        START_DATE("startDate"),
        END_DATE("endDate"),
        CATEGORY("temas.id"),
        TITLE("title");

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
