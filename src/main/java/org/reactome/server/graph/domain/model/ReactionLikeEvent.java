package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.relationship.Input;
import org.reactome.server.graph.domain.relationship.Output;
import org.reactome.server.graph.service.helper.StoichiometryObject;

import java.util.*;

/**
 * Has four subclasses: Reaction, BlackBoxEvent, Polymerisation and Depolymerisation. All involve the conversion of one or more input molecular entities to an output entity, possibly facilitated by a catalyst.
 * <p>
 * Logic in getter/setter of input and output is needed for retrieving data import using the GKInstance.
 * This is still used for testing if graph and sql produce the same data import
 */
@SuppressWarnings("unused")
@NodeEntity
public abstract class ReactionLikeEvent extends Event {

    @ReactomeProperty
    private Boolean isChimeric;
    @ReactomeProperty
    private String systematicName;
    @ReactomeProperty
    private String category;

    @Relationship(type = "catalystActivity")
    private List<CatalystActivity> catalystActivity;

    @Relationship(type = "entityFunctionalStatus")
    private List<EntityFunctionalStatus> entityFunctionalStatus;

    @Relationship(type = "entityOnOtherCell")
    private List<PhysicalEntity> entityOnOtherCell;

    @Relationship(type = "input")
    private Set<Input> input;

    @Relationship(type = "normalReaction")
    private List<ReactionLikeEvent> normalReaction;

    @Relationship(type = "output")
    private Set<Output> output;

    @Relationship(type = "requiredInputComponent")
    private Set<PhysicalEntity> requiredInputComponent;

    @Relationship(type = "regulatedBy")
    private List<Regulation> regulatedBy;

    public ReactionLikeEvent() {
    }

    public Boolean getIsChimeric() {
        return isChimeric;
    }

    public void setIsChimeric(Boolean isChimeric) {
        this.isChimeric = isChimeric;
    }

    public String getSystematicName() {
        return systematicName;
    }

    public void setSystematicName(String systematicName) {
        this.systematicName = systematicName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<CatalystActivity> getCatalystActivity() {
        return catalystActivity;
    }

    @Relationship(type = "catalystActivity")
    public void setCatalystActivity(List<CatalystActivity> catalystActivity) {
        this.catalystActivity = catalystActivity;
    }

    public List<EntityFunctionalStatus> getEntityFunctionalStatus() {
        return entityFunctionalStatus;
    }

    @Relationship(type = "entityFunctionalStatus")
    public void setEntityFunctionalStatus(List<EntityFunctionalStatus> entityFunctionalStatus) {
        this.entityFunctionalStatus = entityFunctionalStatus;
    }

    public List<PhysicalEntity> getEntityOnOtherCell() {
        return entityOnOtherCell;
    }

    @Relationship(type = "entityOnOtherCell")
    public void setEntityOnOtherCell(List<PhysicalEntity> entityOnOtherCell) {
        this.entityOnOtherCell = entityOnOtherCell;
    }

    public List<ReactionLikeEvent> getNormalReaction() {
        return normalReaction;
    }

    @Relationship(type = "normalReaction")
    public void setNormalReaction(List<ReactionLikeEvent> normalReaction) {
        this.normalReaction = normalReaction;
    }

    public Set<PhysicalEntity> getRequiredInputComponent() {
        return requiredInputComponent;
    }

    @Relationship(type = "requiredInputComponent")
    public void setRequiredInputComponent(Set<PhysicalEntity> requiredInputComponent) {
        this.requiredInputComponent = requiredInputComponent;
    }

    @Relationship(type = "regulatedBy")
    public List<Regulation> getRegulatedBy() {
        return regulatedBy;
    }

    @Relationship(type = "regulatedBy")
    public void setRegulatedBy(List<Regulation> regulatedBy) {
        this.regulatedBy = regulatedBy;
    }

    @JsonIgnore
    public List<StoichiometryObject> fetchInput() {
        List<StoichiometryObject> objects = new ArrayList<>();
        if (input != null) {
            for (Input aux : input) {
                objects.add(new StoichiometryObject(aux.getStoichiometry(), aux.getPhysicalEntity()));
            }
            Collections.sort(objects);
        }
        return objects;
    }

    public List<PhysicalEntity> getInput() {
        List<PhysicalEntity> rtn = null;
        if (input != null) {
            rtn = new ArrayList<>();
            for (Input aux : input) {
                for (int i = 0; i < aux.getStoichiometry(); i++) {
                    rtn.add(aux.getPhysicalEntity());
                }
            }
        }
        return rtn;
    }

    public void setInput(List<PhysicalEntity> inputs) {
        if (inputs == null) return;
        // Using LinkedHashMap in order to keep the Collection Sorted previously by AOP
        Map<Long, Input> map = new LinkedHashMap<>();
        for (PhysicalEntity physicalEntity : inputs) {
            Input input = map.get(physicalEntity.getDbId());
            if (input == null) {
                input = new Input();
                input.setReactionLikeEvent(this);
                input.setPhysicalEntity(physicalEntity);
                map.put(physicalEntity.getDbId(), input);
            } else {
                input.setStoichiometry(input.getStoichiometry() + 1);
            }
        }
        this.input = new HashSet<>(map.values());
    }

    @JsonIgnore
    public List<StoichiometryObject> fetchOutput() {
        List<StoichiometryObject> objects = new ArrayList<>();
        if (output != null) {
            for (Output aux : output) {
                objects.add(new StoichiometryObject(aux.getStoichiometry(), aux.getPhysicalEntity()));
            }
            Collections.sort(objects);
        }
        return objects;
    }

    public List<PhysicalEntity> getOutput() {
        List<PhysicalEntity> rtn = null;
        if (output != null) {
            rtn = new ArrayList<>();
            for (Output aux : output) {
                for (int i = 0; i < aux.getStoichiometry(); i++) {
                    rtn.add(aux.getPhysicalEntity());
                }
            }
        }
        return rtn;
    }

    public void setOutput(List<PhysicalEntity> outputs) {
        if (outputs == null) return;
        // Using LinkedHashMap in order to keep the Collection Sorted previously by AOP
        Map<Long, Output> map = new LinkedHashMap<>();
        for (PhysicalEntity physicalEntity : outputs) {
            Output output = map.get(physicalEntity.getDbId());
            if (output == null) {
                output = new Output();
                output.setReactionLikeEvent(this);
                output.setPhysicalEntity(physicalEntity);
                map.put(physicalEntity.getDbId(), output);
            } else {
                output.setStoichiometry(output.getStoichiometry() + 1);
            }
        }
        this.output = new HashSet<>(map.values());
    }

    @ReactomeSchemaIgnore
    @Override
    public String getClassName() {
        return "Reaction";
    }
}
