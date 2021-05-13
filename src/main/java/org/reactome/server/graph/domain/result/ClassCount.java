package org.reactome.server.graph.domain.result;

import org.neo4j.driver.Record;

@Deprecated
public class ClassCount<S,T> {

    public S s;

    public T t;

    public S getS() {
        return s;
    }

    public void setS(S s) {
        this.s = s;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public static ClassCount<String, Integer> build(Record record) {
        ClassCount<String, Integer> classCount = new ClassCount<>();
        classCount.setS(record.get("s").asString());
        classCount.setT(record.get("t").asInt());
        return classCount;
    }
}
