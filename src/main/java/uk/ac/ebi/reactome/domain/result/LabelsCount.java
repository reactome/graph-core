package uk.ac.ebi.reactome.domain.result;

import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 21.01.16.
 *
 *
 * There have been many requests to support the mapping of nodes and relationship entities returned by custom Cypher queries to domain objects. We are happy to announce that this has been included in 2.0.

A query such as

MATCH (user:User {name: {username})-[rating:RATED]->(movie:Movie) RETURN u,r,m


executed by Session.query() will continue to return a Result which contains keys for user, rating and movie but the values are now domain node and relationship entities linked by relationships returned in the query.

This will make it much easier to deal with the results of custom queries and improve performance since your code will no longer need an additional load of the entity by ID.
 */
@QueryResult
public class LabelsCount {

    private Collection<String> labels;
    private int count;

    public Collection<String> getLabels() {
        return labels;
    }

    public void setLabels(Collection<String> labels) {
        this.labels = labels;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
