package org.reactome.server.graph.domain.result;



import org.neo4j.driver.Record;
import org.neo4j.driver.Value;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 21.01.16.
 *
 * Object for retrieving all different labels and their counts from the graph
 */
@SuppressWarnings("unused")
public class SchemaClassCount {

    private int count;
    private Collection<String> labels;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Collection<String> getLabels() {
        return labels;
    }

    public void setLabels(Collection<String> labels) {
        this.labels = labels;
    }

    public static SchemaClassCount build(Record record) {
        SchemaClassCount schemaClassCount = new SchemaClassCount();
        schemaClassCount.setCount(record.get("count").asInt());
        schemaClassCount.setLabels(record.get("labels").asList(Value::asString));
        return schemaClassCount;
    }

}
