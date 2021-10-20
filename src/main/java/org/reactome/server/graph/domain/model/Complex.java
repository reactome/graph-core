package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.relationship.HasCompartment;
import org.reactome.server.graph.domain.relationship.HasComponent;
import org.reactome.server.graph.service.helper.StoichiometryObject;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;

/**
 * An entity formed by the association of two or more component entities (these components can themselves be complexes). Complexes represent all experimentally verified components and their stoichiometry where this is known but may not include as yet unidentified components. At least one component must be specified.
 */
@SuppressWarnings("unused")
@Node
public class Complex extends PhysicalEntity {

    @ReactomeProperty
    private Boolean isChimeric;

    @Relationship(type = "hasComponent")
    private SortedSet<HasComponent> hasComponent;

    @ReactomeProperty
    private Boolean stoichiometryKnown;

    @Relationship(type = "entityOnOtherCell")
    private List<PhysicalEntity> entityOnOtherCell;

    @Relationship(type = "includedLocation")
    private SortedSet<HasCompartment> includedLocation;

    @Relationship(type = "species")
    private List<Species> species;

    @Relationship(type = "relatedSpecies")
    private List<Species> relatedSpecies;

    public Complex() {}

    public Complex(Long dbId) {
        super(dbId);
    }

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
        int order = 0;
        for (PhysicalEntity physicalEntity : hasComponent) {
            HasComponent component = components.get(physicalEntity.getDbId());
            if (component != null) {
                component.setStoichiometry(component.getStoichiometry() + 1);
            } else {
                component = new HasComponent();
                component.setPhysicalEntity(physicalEntity);
                component.setOrder(order++);
                components.put(physicalEntity.getDbId(), component);
            }
        }
        this.hasComponent = new TreeSet<>(components.values());
    }

    public Boolean getStoichiometryKnown() {
        return stoichiometryKnown;
    }

    public void setStoichiometryKnown(Boolean stoichiometryKnown) {
        this.stoichiometryKnown = stoichiometryKnown;
    }

    public List<PhysicalEntity> getEntityOnOtherCell() {
        return entityOnOtherCell;
    }

    public void setEntityOnOtherCell(List<PhysicalEntity> entityOnOtherCell) {
        this.entityOnOtherCell = entityOnOtherCell;
    }

    public List<Compartment> getIncludedLocation() {
        if (includedLocation == null) return null;
        List<Compartment> rtn = new ArrayList<>();
        for (HasCompartment c : includedLocation) {
            rtn.add(c.getCompartment());
        }
        return rtn;
    }

    public void setIncludedLocation(SortedSet<HasCompartment> includedLocation) {
        this.includedLocation = includedLocation;
    }

    public void setIncludedLocation(List<Compartment> includedLocation) {
        this.includedLocation = new TreeSet<>();
        int order = 0;
        for (Compartment c : includedLocation) {
            HasCompartment hc = new HasCompartment();
            hc.setCompartment(c);
            hc.setOrder(order++);
            this.includedLocation.add(hc);
        }
    }

    public List<Species> getSpecies() {
        return species;
    }

    public void setSpecies(List<Species> species) {
        this.species = species;
    }

    public List<Species> getRelatedSpecies() {
        return relatedSpecies;
    }

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
