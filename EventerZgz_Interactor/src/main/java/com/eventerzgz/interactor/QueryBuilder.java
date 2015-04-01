package com.eventerzgz.interactor;


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
        POPULATION("poblacion.id"),
        TITLE("title");

        private final String value;

        private FIELD(final String newValue) {
            value = newValue;
        }

        public String toString() { return value; }
    }

    public QueryBuilder addFilter(FIELD key, COMPARATOR comparator, String value) {
        query.append(key).append(comparator).append(value);
        return this;
    }


    public QueryBuilder and() {
        query.append(';');
        return this;
    }
    public QueryBuilder or() {
        query.append(',');
        return this;
    }
    public QueryBuilder group() {
        query.append('(');
        return this;
    }
    public QueryBuilder ungroup() {
        query.append(')');
        return this;
    }

    public QueryBuilder startToday() {
        String today = DateUtilities.getStartOfToday();
        this.addFilter(QueryBuilder.FIELD.START_DATE, QueryBuilder.COMPARATOR.GREATER_EQUALS, today);
        return this;
    }

    public QueryBuilder isInToday() {
        String today = DateUtilities.getStartOfToday();
        this.addFilter(QueryBuilder.FIELD.START_DATE, COMPARATOR.LESSER_EQUALS, today);
        this.and().addFilter(FIELD.END_DATE, COMPARATOR.GREATER_EQUALS, today);
        return this;
    }

    public QueryBuilder fromToday() {
        String today = DateUtilities.getStartOfToday();
        this.addFilter(QueryBuilder.FIELD.START_DATE, QueryBuilder.COMPARATOR.GREATER_EQUALS, today);
        this.and().addFilter(QueryBuilder.FIELD.END_DATE, QueryBuilder.COMPARATOR.GREATER_EQUALS, today);
        return this;
    }
    public String build() {
        return query.toString();
    }

}
