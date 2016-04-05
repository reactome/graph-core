package org.reactome.server.tools.domain.result;


import org.springframework.data.neo4j.annotation.QueryResult;

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
@QueryResult
public class LabelsCount {

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

}
