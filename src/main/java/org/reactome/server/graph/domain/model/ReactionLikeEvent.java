package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.relationship.Input;
import org.reactome.server.graph.domain.relationship.Output;
import org.reactome.server.graph.service.helper.StoichiometryObject;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;

/**
 * Has four subclasses: Reaction, BlackBoxEvent, Polymerisation and Depolymerisation. All involve the conversion of one or more input molecular entities to an output entity, possibly facilitated by a catalyst.
 * <p>
 * Logic in getter/setter of input and output is needed for retrieving data import using the GKInstance.
 * This is still used for testing if graph and sql produce the same data import
 */
@SuppressWarnings("unused")
@Node
public abstract class ReactionLikeEvent extends Event {

    @ReactomeProperty
    private Boolean isChimeric;
    @ReactomeProperty
    private String systematicName;
    @ReactomeProperty
    private String category;

    @Relationship(type = "catalystActivity")
    private List<CatalystActivity> catalystActivity;

    @Relationship(type = "catalystActivityReference")
    private CatalystActivityReference catalystActivityReference;

    @Relationship(type = "entityFunctionalStatus")
    private List<EntityFunctionalStatus> entityFunctionalStatus;

    @Relationship(type = "entityOnOtherCell")
    private List<PhysicalEntity> entityOnOtherCell;

    @Relationship(type = "input")
    private Set<Input> input;

    @Relationship(type = "output")
    private Set<Output> output;

    @Relationship(type = "normalReaction")
    private ReactionLikeEvent normalReaction;

    @Relationship(type = "regulatedBy")
    private List<Regulation> regulatedBy;

    @Relationship(type = "regulationReference")
    private List<RegulationReference> regulationReference;

    @Relationship(type = "requiredInputComponent")
    private Set<PhysicalEntity> requiredInputComponent;

    @Relationship(type = "hasInteraction")
    private List<InteractionEvent> hasInteraction;

    @Relationship(type = "reactionType")
    private List<ReactionType> reactionType;

    public ReactionLikeEvent() {
    }

    public ReactionLikeEvent(Long dbId) {
        super(dbId);
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

    public void setCatalystActivity(List<CatalystActivity> catalystActivity) {
        this.catalystActivity = catalystActivity;
    }

    public CatalystActivityReference getCatalystActivityReference() {
        return catalystActivityReference;
    }

    public void setCatalystActivityReference(CatalystActivityReference catalystActivityReference) {
        this.catalystActivityReference = catalystActivityReference;
    }

    public List<EntityFunctionalStatus> getEntityFunctionalStatus() {
        return entityFunctionalStatus;
    }

    public void setEntityFunctionalStatus(List<EntityFunctionalStatus> entityFunctionalStatus) {
        this.entityFunctionalStatus = entityFunctionalStatus;
    }

    public List<PhysicalEntity> getEntityOnOtherCell() {
        return entityOnOtherCell;
    }

    public void setEntityOnOtherCell(List<PhysicalEntity> entityOnOtherCell) {
        this.entityOnOtherCell = entityOnOtherCell;
    }

    public ReactionLikeEvent getNormalReaction() {
        return normalReaction;
    }

    public void setNormalReaction(ReactionLikeEvent normalReaction) {
        this.normalReaction = normalReaction;
    }

    public List<Regulation> getRegulatedBy() {
        return regulatedBy;
    }

    public void setRegulatedBy(List<Regulation> regulatedBy) {
        this.regulatedBy = regulatedBy;
    }

    public List<RegulationReference> getRegulationReference() {
        return regulationReference;
    }

    public void setRegulationReference(List<RegulationReference> regulationReference) {
        this.regulationReference = regulationReference;
    }

    public Set<PhysicalEntity> getRequiredInputComponent() {
        return requiredInputComponent;
    }

    public void setRequiredInputComponent(Set<PhysicalEntity> requiredInputComponent) {
        this.requiredInputComponent = requiredInputComponent;
    }

    public List<InteractionEvent> getHasInteraction() {
        return hasInteraction;
    }

    public void setHasInteraction(List<InteractionEvent> hasInteraction) {
        this.hasInteraction = hasInteraction;
    }

    public List<ReactionType> getReactionType() {
        return reactionType;
    }

    public void setReactionType(List<ReactionType> reactionType) {
        this.reactionType = reactionType;
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
//                input.setReactionLikeEvent(this);
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
    //public Set<Output> getOutput(){
    //    return this.output;
    //}

    public void setOutput(Set<Output> output) {
        this.output = output;
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
//                output.setReactionLikeEvent(this);
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
