package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.domain.relationship.RepeatedUnit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NodeEntity
public class Polymer extends PhysicalEntity {//implements Modification

    private Integer maxUnitCount;
    private Integer minUnitCount;
    private String totalProt;
    private String maxHomologues;
    private String inferredProt;

    @Relationship(type = "repeatedUnit")
    private RepeatedUnit repeatedUnit;

    @Relationship(type = "species")
    private Set<Species> species;

    public Polymer() {}

    public Integer getMaxUnitCount() {
        return maxUnitCount;
    }

    public void setMaxUnitCount(Integer maxUnitCount) {
        this.maxUnitCount = maxUnitCount;
    }

    public Integer getMinUnitCount() {
        return minUnitCount;
    }

    public void setMinUnitCount(Integer minUnitCount) {
        this.minUnitCount = minUnitCount;
    }

    public String getTotalProt() {
        return totalProt;
    }

    public void setTotalProt(String totalProt) {
        this.totalProt = totalProt;
    }

    public String getMaxHomologues() {
        return maxHomologues;
    }

    public void setMaxHomologues(String maxHomologues) {
        this.maxHomologues = maxHomologues;
    }

    public String getInferredProt() {
        return inferredProt;
    }

    public void setInferredProt(String inferredProt) {
        this.inferredProt = inferredProt;
    }




//    public RepeatedUnit getRepeatedUnit() {
//        return repeatedUnit;
//    }
//
//    public void setRepeatedUnit(RepeatedUnit repeatedUnit) {
//        this.repeatedUnit = repeatedUnit;
//    }

    public Set<Species> getSpecies() {
        return species;
    }

    public void setSpecies(Set<Species> species) {
        this.species = species;
    }

    public void setSpecies(Species species) {
        Set<Species> speciesSet = new HashSet<>();
        speciesSet.add(species);
        this.species = speciesSet;
    }




    public List<PhysicalEntity> getRepeatedUnit() {
        List<PhysicalEntity> rtn = new ArrayList<>();
        if(repeatedUnit!=null){
            for (int i = 0; i < repeatedUnit.getCardinality(); i++) {
                rtn.add(repeatedUnit.getPhysicalEntity());
            }
        }
        return rtn;
    }

    public void setRepeatedUnit(List<PhysicalEntity> repeatedUnit) {
        if (repeatedUnit != null && repeatedUnit.isEmpty()) {
            this.repeatedUnit = new RepeatedUnit();
            this.repeatedUnit.setPolymer(this);
            this.repeatedUnit.setPhysicalEntity(repeatedUnit.get(0));
            this.repeatedUnit.setCardinality(repeatedUnit.size());
        }
    }

}
