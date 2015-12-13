package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class EntitySet extends PhysicalEntity {

    private String totalProt;

    private String inferredProt;

    private String maxHomologues;

    @Relationship
    private List<PhysicalEntity> hasMember;

    @Relationship
    private List<Species> species;

    private Boolean isOrdered;

    public EntitySet() {
    }
    
    public Boolean getIsOrdered() {
        return isOrdered;
    }

    public void setIsOrdered(Boolean isOrdered) {
        this.isOrdered = isOrdered;
    }

    public String getTotalProt() {
        return this.totalProt;
    }

    public void setTotalProt(String totalProt) {
        this.totalProt = totalProt;
    }

    public String getInferredProt() {
        return this.inferredProt;
    }

    public List<PhysicalEntity> getHasMember() {
        return hasMember;
    }

    public void setHasMember(List<PhysicalEntity> hasMember) {
        this.hasMember = hasMember;
    }

    public List<Species> getSpecies() {
        return species;
    }

    public void setSpecies(List<Species> species) {
        this.species = species;
    }

    public void setInferredProt(String inferredProt) {
        this.inferredProt = inferredProt;
    }

    public String getMaxHomologues() {
        return this.maxHomologues;
    }

    public void setMaxHomologues(String maxHomologues) {
        this.maxHomologues = maxHomologues;
    }

}
