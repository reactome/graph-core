package org.reactome.server.graph.domain.result;

import org.neo4j.driver.Record;

public class InteractorsCount {

    public String acc;
    public Integer count;

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public static InteractorsCount build(Record record) {
        InteractorsCount interactorsCount = new InteractorsCount();
        interactorsCount.setAcc(record.get("s").asString());
        interactorsCount.setCount(record.get("t").asInt());
        return interactorsCount;
    }
}
