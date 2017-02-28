package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.relationship.HasComponent;
import org.reactome.server.graph.service.helper.StoichiometryObject;

import java.util.*;

/**
 * An entity formed by the association of two or more component entities (these components can themselves be complexes). Complexes represent all experimentally verified components and their stoichiometry where this is known but may not include as yet unidentified components. At least one component must be specified.
 */
@SuppressWarnings("unused")
@NodeEntity
public class Complex extends PhysicalEntity {

    @ReactomeProperty
    private Boolean isChimeric;

    @Relationship(type = "hasComponent", direction = Relationship.OUTGOING)
    private Set<HasComponent> hasComponent;

    @Relationship(type = "entityOnOtherCell", direction = Relationship.OUTGOING)
    private List<PhysicalEntity> entityOnOtherCell;

    @Relationship(type = "includedLocation", direction = Relationship.OUTGOING)
    private List<EntityCompartment> includedLocation;

    @Relationship(type = "species", direction = Relationship.OUTGOING)
    private List<Species> species;

    @Relationship(type = "relatedSpecies", direction = Relationship.OUTGOING)
    private List<Species> relatedSpecies;

    public Complex() {}

    public Boolean getIsChimeric() {
        return isChimeric;
    }

    public void setIsChimeric(Boolean isChimeric) {
        this.isChimeric = isChimeric;
    }

    @JsonIgnore
    public List<StoichiometryObject> fetchHasComponent() {
        List<StoichiometryObject> objects = new ArrayList<>();
        if(hasComponent!=null) {
            for (HasComponent aux : hasComponent) {
                objects.add(new StoichiometryObject(aux.getStoichiometry(), aux.getPhysicalEntity()));
            }
            Collections.sort(objects);
        }

        return objects;
    }

    public List<PhysicalEntity> getHasComponent(){
        List<PhysicalEntity> rtn = null;
        if (this.hasComponent != null) {
            rtn = new ArrayList<>();
            for (HasComponent component : this.hasComponent) {
                for (int i = 0; i < component.getStoichiometry(); i++) {
                    rtn.add(component.getPhysicalEntity());
                }
            }
        }
        return rtn;
    }

    public void setHasComponent(List<PhysicalEntity> hasComponent) {
        if (hasComponent == null) return;
        Map<Long, HasComponent> components = new LinkedHashMap<>();
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

    @Relationship(type = "entityOnOtherCell", direction = Relationship.OUTGOING)
    public void setEntityOnOtherCell(List<PhysicalEntity> entityOnOtherCell) {
        this.entityOnOtherCell = entityOnOtherCell;
    }

    public List<EntityCompartment> getIncludedLocation() {
        return includedLocation;
    }

    @Relationship(type = "includedLocation", direction = Relationship.OUTGOING)
    public void setIncludedLocation(List<EntityCompartment> includedLocation) {
        this.includedLocation = includedLocation;
    }

    public List<Species> getSpecies() {
        return species;
    }

    @Relationship(type = "species", direction = Relationship.OUTGOING)
    public void setSpecies(List<Species> species) {
        this.species = species;
    }

    public List<Species> getRelatedSpecies() {
        return relatedSpecies;
    }

    @Relationship(type = "relatedSpecies", direction = Relationship.OUTGOING)
    public void setRelatedSpecies(List<Species> relatedSpecies) {
        this.relatedSpecies = relatedSpecies;
    }

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "An entity formed by the association of two or more component entities (these components can themselves be complexes). " +
                "At least one component must be specified. Complexes represent all experimentally verified components and their stoichiometry where this is known but may not include as yet unidentified components";
    }
}
