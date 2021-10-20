package org.reactome.server.graph.domain.result;

import org.neo4j.driver.Record;

/**
 * When a custom class has to be mapped using Custom queries, the code expect this interface
 * to be implemented and that you provide your own way of mapping your class
 * 
 *     public CustomQuery build(Record r) {
 *         this.setStId(r.get("stId").asString());
 *         this.setDisplayName(r.get("displayName").asString());
 *         this.setCustomReference(new CustomReference(r.get("customReference").get("database").asString(), r.get("customReference").get("identifier").asString()));
 *         return this;
 *     }
 *     
 */
public interface CustomQuery {
    CustomQuery build(Record r);
}
