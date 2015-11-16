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
 * Regulation is a container class. Regulations represent physicalEntities that can have a regulatory effect on
 * Events or CatalystActivities. ReactionLikeEvents can be present in multiple Pathway, it can occur that a Reaction
 * will be regulated in Pathway A but not in Pathway B, therefore IN_PATHWAY relationship can be set.
 * Regulation type specifies how the Regulation works (for example through phosphorylation)
 */
@NodeEntity
public abstract class Regulation extends DatabaseObject {

    private String regulationType;

    @Relationship(type = "REGULATES", direction = Relationship.INCOMING)
    private DatabaseObject regulator;

    @Relationship(type = "IN_PATHWAY", direction = Relationship.INCOMING)
    private Set<Pathway> pathway;

    public Regulation() {}

    public Regulation(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }

    public String getRegulationType() {
        return regulationType;
    }

    @SuppressWarnings("unused")
    public void setRegulationType(String regulationType) {
        this.regulationType = regulationType;
    }

    public DatabaseObject getRegulator() {
        return regulator;
    }

    @SuppressWarnings("unused")
    public void setRegulator(DatabaseObject regulator) {
        this.regulator = regulator;
    }

    public Set<Pathway> getPathway() {
        return pathway;
    }

    @SuppressWarnings("unused")
    public void setPathway(Set<Pathway> pathway) {
        this.pathway = pathway;
    }
}
