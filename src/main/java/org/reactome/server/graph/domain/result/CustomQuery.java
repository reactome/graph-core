package org.reactome.server.graph.domain.result;

import org.neo4j.driver.Record;

/**
 * When a custom class has to be mapped using Custom queries, the code expect this interface
 * to be implement and that you provide your own way of mapping your class.
 */
public interface CustomQuery {
    CustomQuery build(Record r);
}
