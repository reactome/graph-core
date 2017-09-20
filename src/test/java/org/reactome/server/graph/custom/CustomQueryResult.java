package org.reactome.server.graph.custom;

import java.util.List;

/**
 * Custom object used to test the custom queries methods
 *
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
@SuppressWarnings("unused")
public class CustomQueryResult {
    private Long dbId;
    private String name;
    private List<Long> events;
    private String[] eventsArray;
    private int[] eventsPrimitiveArray;

    public CustomQueryResult() {
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getEvents() {
        return events;
    }

    public void setEvents(List<Long> events) {
        this.events = events;
    }

    public String[] getEventsArray() {
        return eventsArray;
    }

    public void setEventsArray(String[] eventsArray) {
        this.eventsArray = eventsArray;
    }

    public int[] getEventsPrimitiveArray() {
        return eventsPrimitiveArray;
    }

    public void setEventsPrimitiveArray(int[] eventsPrimitiveArray) {
        this.eventsPrimitiveArray = eventsPrimitiveArray;
    }
}
