package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@SuppressWarnings("unused")
@NodeEntity
public class OpenSet extends EntitySet {

    @Relationship(type = "referenceEntity", direction = Relationship.OUTGOING)
    private ReferenceEntity referenceEntity;

    public OpenSet() {}

    public ReferenceEntity getReferenceEntity() {
        return referenceEntity;
    }

    public void setReferenceEntity(ReferenceEntity referenceEntity) {
        this.referenceEntity = referenceEntity;
    }

}
