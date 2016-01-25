package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class EntitySet extends PhysicalEntity{ //implements Modification

//    private String totalProt;
//    private String inferredProt;
//    private String maxHomologues;
    private Boolean isOrdered;

    @Relationship(type = "hasMember")
    private List<PhysicalEntity> hasMember;

    @Relationship(type = "species")
    private List<Species> species;

    public EntitySet() {}

//    public String getTotalProt() {
//        return totalProt;
//    }
//
//    public void setTotalProt(String totalProt) {
//        this.totalProt = totalProt;
//    }
//
//    public String getInferredProt() {
//        return inferredProt;
//    }
//
//    public void setInferredProt(String inferredProt) {
//        this.inferredProt = inferredProt;
//    }
//
//    public String getMaxHomologues() {
//        return maxHomologues;
//    }
//
//    public void setMaxHomologues(String maxHomologues) {
//        this.maxHomologues = maxHomologues;
//    }

    public Boolean getIsOrdered() {
        return isOrdered;
    }

    public void setIsOrdered(Boolean isOrdered) {
        this.isOrdered = isOrdered;
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
}
