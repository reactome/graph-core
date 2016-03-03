package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.domain.relationship.HasComponent;

import java.util.*;

@SuppressWarnings("unused")
@NodeEntity
public class Complex extends PhysicalEntity {

    private Boolean isChimeric;

    @Relationship(type = "hasComponent")
    private Set<HasComponent> hasComponent;

    @Relationship(type = "entityOnOtherCell")
    private List<PhysicalEntity> entityOnOtherCell;

    @Relationship(type = "includedLocation")
    private List<EntityCompartment> includedLocation;

    @Relationship(type = "species")
    private List<Species> species;

    public Complex() {}

    public Boolean getIsChimeric() {
        return isChimeric;
    }

    public void setIsChimeric(Boolean isChimeric) {
        this.isChimeric = isChimeric;
    }

    public List<PhysicalEntity> getHasComponent(){
        List<PhysicalEntity> rtn = new ArrayList<>();
        for (HasComponent component : this.hasComponent) {
            for (int i = 0; i < component.getStoichiometry(); i++) {
                rtn.add(component.getPhysicalEntity());
            }
        }
        return rtn;
    }

    public void setHasComponent(List<PhysicalEntity> hasComponent) {
        if (hasComponent == null) return;
        Map<Long, HasComponent> components = new HashMap<>();
        for (PhysicalEntity physicalEntity : hasComponent) {
            HasComponent component = components.get(physicalEntity.getDbId());
            if (component != null) {
                component.setStoichiometry(component.getStoichiometry() + 1);
            } else {
                component = new HasComponent();
                component.setComplex(this);
                component.setPhysicalEntity(physicalEntity);
                components.put(physicalEntity.getDbId(), component);
            }
        }
        this.hasComponent = new HashSet<>(components.values());
    }

    public List<PhysicalEntity> getEntityOnOtherCell() {
        return entityOnOtherCell;
    }

    public void setEntityOnOtherCell(List<PhysicalEntity> entityOnOtherCell) {
        this.entityOnOtherCell = entityOnOtherCell;
    }

    public List<EntityCompartment> getIncludedLocation() {
        return includedLocation;
    }

    public void setIncludedLocation(List<EntityCompartment> includedLocation) {
        this.includedLocation = includedLocation;
    }

    public List<Species> getSpecies() {
        return species;
    }

    public void setSpecies(List<Species> species) {
        this.species = species;
    }
}
