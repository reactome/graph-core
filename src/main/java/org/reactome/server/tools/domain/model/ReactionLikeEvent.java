package org.reactome.server.tools.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.tools.domain.annotations.ReactomeProperty;
import org.reactome.server.tools.domain.relationship.Input;
import org.reactome.server.tools.domain.relationship.Output;

import java.util.*;

/**
 * Has four subclasses: Reaction, BlackBoxEvent, Polymerisation and Depolymerisation. All involve the conversion of one or more input molecular entities to an output entity, possibly facilitated by a catalyst.
 *
 * Logic in getter/setter of input and output is needed for retrieving dataimport using the GKInstance.
 * This is still used for testing if graph and sql produce the same dataimport
 */
@SuppressWarnings("unused")
@NodeEntity
public abstract class ReactionLikeEvent extends Event {

    @ReactomeProperty
    private Boolean isChimeric;
    @ReactomeProperty
    private String systematicName;

    @Relationship(type = "catalystActivity", direction = Relationship.OUTGOING)
    private List<CatalystActivity> catalystActivity;

    @Relationship(type = "entityFunctionalStatus", direction = Relationship.OUTGOING)
    private List<EntityFunctionalStatus> entityFunctionalStatus;

    @Relationship(type = "entityOnOtherCell", direction = Relationship.OUTGOING)
    private List<PhysicalEntity> entityOnOtherCell;

    @Relationship(type = "input", direction = Relationship.OUTGOING)
    private List<Input> input;

    @Relationship(type = "normalReaction", direction = Relationship.OUTGOING)
    private List<ReactionLikeEvent> normalReaction;

    @Relationship(type = "output", direction = Relationship.OUTGOING)
    private List<Output> output;

    @Relationship(type = "requiredInputComponent", direction = Relationship.OUTGOING)
    private Set<PhysicalEntity> requiredInputComponent;

    public ReactionLikeEvent() {}


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

    public List<CatalystActivity> getCatalystActivity() {
        return catalystActivity;
    }

    public void setCatalystActivity(List<CatalystActivity> catalystActivity) {
        this.catalystActivity = catalystActivity;
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

    public List<ReactionLikeEvent> getNormalReaction() {
        return normalReaction;
    }

    public void setNormalReaction(List<ReactionLikeEvent> normalReaction) {
        this.normalReaction = normalReaction;
    }

    public Set<PhysicalEntity> getRequiredInputComponent() {
        return requiredInputComponent;
    }

    public void setRequiredInputComponent(Set<PhysicalEntity> requiredInputComponent) {
        this.requiredInputComponent = requiredInputComponent;
    }

    public List<PhysicalEntity> getInput() {
        List<PhysicalEntity> rtn = new ArrayList<>();
        if(input!=null) {
            for (Input aux : input) {
                for (int i = 0; i < aux.getStoichiometry(); i++) {
                    rtn.add(aux.getPhysicalEntity());
                }
            }
            return rtn;
        }
        return null;
    }

    public void setInput(List<PhysicalEntity> inputs) {
        if(inputs == null) return;
        Map<Long, Input> map = new HashMap<>();
        for (PhysicalEntity physicalEntity : inputs) {
            Input input = map.get(physicalEntity.getDbId());
            if (input == null) {
                input = new Input();
                input.setEvent(this);
                input.setPhysicalEntity(physicalEntity);
                map.put(physicalEntity.getDbId(), input);
            } else {
                input.setStoichiometry(input.getStoichiometry() + 1);
            }
        }
        this.input = new ArrayList<>(map.values());
    }

    public List<PhysicalEntity> getOutput() {
        List<PhysicalEntity> rtn = new ArrayList<>();
        if (output != null) {
            for (Output aux : output) {
                for (int i = 0; i < aux.getStoichiometry(); i++) {
                    rtn.add(aux.getPhysicalEntity());
                }
            }
            return rtn;
        }
        return null;
    }

    public void setOutput(List<PhysicalEntity> outputs) {
        if(outputs == null) return;
        Map<Long, Output> map = new HashMap<>();
        for (PhysicalEntity physicalEntity : outputs) {
            Output output = map.get(physicalEntity.getDbId());
            if (output == null) {
                output = new Output();
                output.setEvent(this);
                output.setPhysicalEntity(physicalEntity);
                map.put(physicalEntity.getDbId(), output);
            } else {
                output.setStoichiometry(output.getStoichiometry() + 1);
            }
        }
        this.output = new ArrayList<>(map.values());
    }

    @Override
    public String getClassName() {
        return "Reaction";
    }

}
