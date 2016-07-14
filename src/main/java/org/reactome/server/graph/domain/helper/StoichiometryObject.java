package org.reactome.server.graph.domain.helper;

import org.reactome.server.graph.domain.model.DatabaseObject;

/**
 * Created by flo on 14/07/16.
 */
public class StoichiometryObject {

    private Integer stoichiometry;

    private DatabaseObject object;

    public StoichiometryObject(Integer stoichiometry, DatabaseObject object) {
        this.stoichiometry = stoichiometry;
        this.object = object;
    }

    public Integer getStoichiometry() {
        return stoichiometry;
    }

    public void setStoichiometry(Integer stoichiometry) {
        this.stoichiometry = stoichiometry;
    }

    public DatabaseObject getObject() {
        return object;
    }

    public void setObject(DatabaseObject object) {
        this.object = object;
    }
}
