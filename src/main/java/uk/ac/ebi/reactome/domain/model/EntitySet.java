package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * EntitySet is a container class. Sets describe PhysicalEntities with interchangeable function
 */
@NodeEntity
public abstract class EntitySet extends PhysicalEntity {

    @Relationship(type = "HAS_MEMBER", direction = Relationship.OUTGOING)
    private Set<PhysicalEntity> members;

    public EntitySet() {}

    public EntitySet(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }

    public Set<PhysicalEntity> getMembers() {
        return members;
    }

    @SuppressWarnings("unused")
    public void setMembers(Set<PhysicalEntity> members) {
        this.members = members;
    }
}
