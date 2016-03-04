package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.domain.relationship.RepeatedUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Molecules that consist of an indeterminate number of repeated units. Includes complexes whose stoichiometry is variable or unknown.
 * The repeated unit(s) is(are) identified in the repeatedUnit slot.
 *
 * Logic in getter/setter of input and output is needed for retrieving data using the GKInstance.
 * This is still used for testing if graph and sql produce the same data
 */
@SuppressWarnings("unused")
@NodeEntity
public class Polymer extends PhysicalEntity {

    private Integer maxUnitCount;
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

    public List<PhysicalEntity> getRepeatedUnit() {
        List<PhysicalEntity> rtn = new ArrayList<>();
        if(repeatedUnit!=null){
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

    public void setSpecies(Set<Species> species) {
        this.species = species;
    }

/*   public void setSpecies(Species species) {
        Set<Species> speciesSet = new HashSet<>();
        speciesSet.add(species);
        this.species = speciesSet;
    }

*/

}
