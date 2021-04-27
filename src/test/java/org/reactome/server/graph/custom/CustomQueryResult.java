package org.reactome.server.graph.custom;

import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.reactome.server.graph.domain.result.CustomQuery;

import java.util.List;

/**
 * Custom object used to test the custom queries methods
 *
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
@SuppressWarnings("unused")
public class CustomQueryResult implements CustomQuery {
    private Long dbId;
    private String name;
    private List<Long> events;
    private String[] eventsArray;
    private int[] eventsPrimitiveArray;

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

    @Override
    public CustomQuery build(Record r) {
        this.setDbId(r.get("dbId").asLong());
        this.setName(r.get("name").asString());
        this.setEvents(r.get("events").asList(Value::asLong));
        this.setEventsArray(r.get("eventsArray").asList(Value::asString).toArray(new String[0]));
        this.setEventsPrimitiveArray(r.get("eventsPrimitiveArray").asList(Value::asInt).stream().mapToInt(Integer::intValue).toArray());
        return this;
    }
}
