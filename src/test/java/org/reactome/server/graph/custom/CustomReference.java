package org.reactome.server.graph.custom;

/**
 * POJO for testing the Custom Cypher Queries
 *
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
@SuppressWarnings("unused")
public class CustomReference {
    private String database;
    private String identifier;

    public CustomReference(String database, String identifier) {
        this.database = database;
        this.identifier = identifier;
    }

    public String getDatabase() {
        return database;
    }

//    Commented out in purpose so the marshaller uses the field -> FOR TEST PURPOSES!
//    public void setDatabase(String database) {
//        this.database = database;
//    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
