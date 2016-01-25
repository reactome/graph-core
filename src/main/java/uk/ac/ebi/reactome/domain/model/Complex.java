package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.domain.relationship.HasComponent;

import java.util.*;

@NodeEntity
public class Complex extends PhysicalEntity {

    private Boolean isChimeric;
//    private String totalProt;
//    private String maxHomologues;
//    private String inferredProt;

    @Relationship(type = "hasComponent")
    private Set<HasComponent> hasComponent;

//    @Relationship(type = "entityOnOthercell")
//    private List<PhysicalEntity> entityOnOthercell;

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

//    public String getTotalProt() {
//        return totalProt;
//    }
//
//    public void setTotalProt(String totalProt) {
//        this.totalProt = totalProt;
//    }
//
//    public String getMaxHomologues() {
//        return maxHomologues;
//    }
//
//    public void setMaxHomologues(String maxHomologues) {
//        this.maxHomologues = maxHomologues;
//    }
//
//    public String getInferredProt() {
//        return inferredProt;
//    }
//
//    public void setInferredProt(String inferredProt) {
//        this.inferredProt = inferredProt;
//    }

//    public Set<HasComponent> getHasComponent() {
//        return hasComponent;
//    }
//
//    public void setHasComponent(Set<HasComponent> hasComponent) {
//        this.hasComponent = hasComponent;
//    }

//    public List<PhysicalEntity> getEntityOnOthercell() {
//        return entityOnOthercell;
//    }
//
//    public void setEntityOnOthercell(List<PhysicalEntity> entityOnOthercell) {
//        this.entityOnOthercell = entityOnOthercell;
//    }

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

//    public Set<HasComponent> getHasComponent() {
//        return hasComponent;
//    }
//
//    public void setHasComponent(Set<HasComponent> hasComponent) {
//        this.hasComponent = hasComponent;
//    }

        public List<PhysicalEntity> getHasComponent(){
        List<PhysicalEntity> rtn = new ArrayList<>();
        for (HasComponent component : this.hasComponent) {
            for (int i = 0; i < component.getCardinality(); i++) {
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
                component.setCardinality(component.getCardinality() + 1);
            } else {
                component = new HasComponent();
                component.setComplex(this);
                component.setPhysicalEntity(physicalEntity);
                components.put(physicalEntity.getDbId(), component);
            }
        }
        this.hasComponent = new HashSet<>(components.values());
    }


}
