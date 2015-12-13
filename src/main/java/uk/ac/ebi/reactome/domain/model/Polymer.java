package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.domain.relationship.RepeatedUnit;

import java.util.*;

@NodeEntity
public class Polymer extends PhysicalEntity {

    private Integer maxUnitCount;
    private Integer minUnitCount;
    private String totalProt;
    private String maxHomologues;
    private String inferredProt;
    @Relationship(type = "HAS_REPEATED_UNIT", direction = Relationship.OUTGOING)
    private RepeatedUnit repeatedUnit;
    @Relationship
    private Set<Species> species;

    public Polymer() {
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

    public void setRepeatedUnit(RepeatedUnit repeatedUnit) {
        this.repeatedUnit = repeatedUnit;
    }

    public Set<Species> getSpecies() {
        return species;
    }

    public void setSpecies(Collection<Species> species) {
        this.species = new HashSet<>(species);
    }

    public Integer getMaxUnitCount() {
        return this.maxUnitCount;
    }

    public void setMaxUnitCount(Integer maxUnitCount) {
        this.maxUnitCount = maxUnitCount;
    }

    public Integer getMinUnitCount() {
        return this.minUnitCount;
    }

    public void setMinUnitCount(Integer minUnitCount) {
        this.minUnitCount = minUnitCount;
    }

    public String getTotalProt() {
        return this.totalProt;
    }

    public void setTotalProt(String totalProt) {
        this.totalProt = totalProt;
    }

    public String getMaxHomologues() {
        return this.maxHomologues;
    }

    public void setMaxHomologues(String maxHomologues) {
        this.maxHomologues = maxHomologues;
    }

    public String getInferredProt() {
        return this.inferredProt;
    }

    public void setInferredProt(String inferredProt) {
        this.inferredProt = inferredProt;
    }

}
