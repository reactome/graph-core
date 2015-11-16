package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 13.11.15.
 */
@NodeEntity
public abstract class ReferenceEntity extends DatabaseObject {

    private String identifier;

//    @Relationship(type = "INTERACTS_WITH", direction = Relationship.OUTGOING)
//    private Set<Interaction> interactions;

    public ReferenceEntity() {}

    public ReferenceEntity(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
