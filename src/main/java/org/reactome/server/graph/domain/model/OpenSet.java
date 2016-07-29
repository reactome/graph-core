package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;

/**
 * A group of entities that could be listed in principle but not in practice, such as mRNA or long-chain fatty acid. Examples can be specified as values of the hasMember slot, but are not an exhaustive list of the possible members. The referenceEntity slot indicates the chemical feature that is common to all the members of this class, e.g. The OpenSet  for Alcohol  would have the -OH group defined for referenceEntity.
 */
@SuppressWarnings("unused")
@NodeEntity
public class OpenSet extends EntitySet {

    @ReactomeProperty
    private String referenceType;

    @Relationship(type = "referenceEntity", direction = Relationship.OUTGOING)
    private ReferenceEntity referenceEntity;

    public OpenSet() {}

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public ReferenceEntity getReferenceEntity() {
        return referenceEntity;
    }

    public void setReferenceEntity(ReferenceEntity referenceEntity) {
        this.referenceEntity = referenceEntity;
    }

    @ReactomeSchemaIgnore
    @Override
    public String getExplanation() {
        return "A group of entities that could be listed in principle but not in practice, such as mRNA or long-chain fatty acid. " +
                "Examples can be specified as values of the hasMember slot, but are not an exhaustive list of the possible members. The referenceEntity slot indicates the chemical feature that is common to all the members of this class, e.g. The OpenSet  for Alcohol  would have the -OH group defined for referenceEntity";
    }
}
