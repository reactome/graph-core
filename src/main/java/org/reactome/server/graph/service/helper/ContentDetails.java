package org.reactome.server.graph.service.helper;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.domain.result.ComponentOf;

import java.util.Collection;
import java.util.Set;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 14.04.16.
 */
public class ContentDetails {

    private DatabaseObject databaseObject;
    private Set<PathwayBrowserNode> leaves;
    private Collection<ComponentOf> componentOf;
    private Collection<PhysicalEntity> otherFormsOfThisMolecule;

    public ContentDetails() {}

    public Collection<ComponentOf> getComponentOf() {
        return componentOf;
    }

    public void setComponentOf(Collection<ComponentOf> componentOf) {
        this.componentOf = componentOf;
    }

    public DatabaseObject getDatabaseObject() {
        return databaseObject;
    }

    public void setDatabaseObject(DatabaseObject databaseObject) {
        this.databaseObject = databaseObject;
    }

    public Set<PathwayBrowserNode> getLeaves() {
        return leaves;
    }

    public void setLeaves(Set<PathwayBrowserNode> leaves) {
        this.leaves = leaves;
    }

    public Collection<PhysicalEntity> getOtherFormsOfThisMolecule() {
        return otherFormsOfThisMolecule;
    }

    public void setOtherFormsOfThisMolecule(Collection<PhysicalEntity> otherFormsOfThisMolecule) {
        this.otherFormsOfThisMolecule = otherFormsOfThisMolecule;
    }
}
