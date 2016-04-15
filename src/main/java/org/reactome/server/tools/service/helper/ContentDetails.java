package org.reactome.server.tools.service.helper;

import org.reactome.server.tools.domain.model.DatabaseObject;
import org.reactome.server.tools.domain.model.PhysicalEntity;

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
    private Set<PBNode> leafs;
    private Collection<PhysicalEntity> otherFormsOfThisMolecule;

    public ContentDetails() {}

    public ContentDetails(DatabaseObject databaseObject, Set<PBNode> leafs, Set<PhysicalEntity> otherFormsOfThisMolecule) {
        this.databaseObject = databaseObject;
        this.leafs = leafs;
        this.otherFormsOfThisMolecule = otherFormsOfThisMolecule;
    }

    public DatabaseObject getDatabaseObject() {
        return databaseObject;
    }

    public void setDatabaseObject(DatabaseObject databaseObject) {
        this.databaseObject = databaseObject;
    }

    public Set<PBNode> getLeafs() {
        return leafs;
    }

    public void setLeafs(Set<PBNode> leafs) {
        this.leafs = leafs;
    }

    public Collection<PhysicalEntity> getOtherFormsOfThisMolecule() {
        return otherFormsOfThisMolecule;
    }

    public void setOtherFormsOfThisMolecule(Collection<PhysicalEntity> otherFormsOfThisMolecule) {
        this.otherFormsOfThisMolecule = otherFormsOfThisMolecule;
    }
}
