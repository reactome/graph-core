package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.relationship.RepeatedUnit;
import org.reactome.server.graph.service.helper.StoichiometryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Molecules that consist of an indeterminate number of repeated units. Includes complexes whose stoichiometry is variable or unknown. The repeated unit(s) is(are) identified in the repeatedUnit slot.
 *
 * Logic in getter/setter of input and output is needed for retrieving data import using the GKInstance.
 * This is still used for testing if graph and sql produce the same data import
 */
@SuppressWarnings("unused")
@NodeEntity
public class Polymer extends PhysicalEntity {

    @ReactomeProperty
    private Integer maxUnitCount;
    @ReactomeProperty
    private Integer minUnitCount;

    @Relationship(type = "repeatedUnit", direction = Relationship.OUTGOING)
    private RepeatedUnit repeatedUnit;

    @Relationship(type = "species", direction = Relationship.OUTGOING)
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

    @JsonIgnore
    public StoichiometryObject fetchRepeatedUnit() {
        if(repeatedUnit!=null) {
            return new StoichiometryObject(repeatedUnit.getStoichiometry(), repeatedUnit.getPhysicalEntity());
        }
        return null;
    }

    public List<PhysicalEntity> getRepeatedUnit() {
        List<PhysicalEntity> rtn = null;
        if(repeatedUnit!=null){
            rtn = new ArrayList<>();
            for (int i = 0; i < repeatedUnit.getStoichiometry(); i++) {
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
            this.repeatedUnit.setStoichiometry(repeatedUnit.size());
        }
    }

    public Set<Species> getSpecies() {
        return species;
    }

    @Relationship(type = "species", direction = Relationship.OUTGOING)
    public void setSpecies(Set<Species> species) {
        this.species = species;
    }

/*   public void setSpecies(Species species) {
        Set<Species> speciesSet = new HashSet<>();
        speciesSet.add(species);
        this.species = speciesSet;
    }

*/

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "Molecules that consist of an indeterminate number of repeated units. Includes complexes whose stoichiometry is variable or unknown. " +
                "The repeated unit(s) is(are) identified in the repeatedUnit slot";
    }
}
