package org.reactome.server.graph.service.helper;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.springframework.lang.NonNull;

@SuppressWarnings("unused")
public class StoichiometryObject implements Comparable<StoichiometryObject> {

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

    public <T extends DatabaseObject> T getObject() {
        //noinspection unchecked
        return (T) object;
    }

    public void setObject(DatabaseObject object) {
        this.object = object;
    }

    @Override
    public int compareTo(@NonNull StoichiometryObject o) {
        return this.object.getDisplayName().compareToIgnoreCase(o.getObject().getDisplayName());
    }
}
