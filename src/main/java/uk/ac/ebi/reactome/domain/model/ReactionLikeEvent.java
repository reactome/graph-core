package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.stereotype.Component;
import uk.ac.ebi.reactome.domain.relationship.Input;
import uk.ac.ebi.reactome.domain.relationship.Output;

import java.util.List;
import java.util.Set;

@Component
@NodeEntity
public class ReactionLikeEvent extends Event {

    private Boolean isChimeric;
    private String systematicName;


    @Relationship(type = "input")
    private Set<Input> input;
    @Relationship(type = "output")
    private Set<Output> output;
    @Relationship
    private List<PhysicalEntity> entityOnOtherCell;
    @Relationship
    private List<DatabaseObject> requiredInputComponent;
    @Relationship
    private ReactionLikeEvent hasMember;
    @Relationship
    private List<CatalystActivity> catalystActivity;
    @Relationship
    private List<ReactionLikeEvent> normalReaction;
    @Relationship
    private List<EntityFunctionalStatus> entityFunctionalStatus;


    public String getSystematicName() {
        return systematicName;
    }

    public void setSystematicName(String systematicName) {
        this.systematicName = systematicName;
    }

//    public Set<Input> getInput() {
//        return input;
//    }
//
//    public void setInput(Set<Input> input) {
//        this.input = input;
//    }
//
//    public Set<Output> getOutput() {
//        return output;
//    }
//
//    public void setOutput(Set<Output> output) {
//        this.output = output;
//    }

//        public List<PhysicalEntity> getInput() {
//        List<PhysicalEntity> rtn = new ArrayList<>();
//        for (Input aux : input) {
//            for (int i = 0; i < aux.getCardinality(); i++) {
//                rtn.add(aux.getPhysicalEntity());
//            }
//        }
//        return rtn;
//    }
//
//    public void setInput(List<PhysicalEntity> inputs) {
//        Map<Long, Input> map = new HashMap<>();
//        for (PhysicalEntity physicalEntity : inputs) {
//            Input input = map.get(physicalEntity.getDbId());
//            if (input == null) {
//                input = new Input();
//                input.setEvent(this);
//                input.setPhysicalEntity(physicalEntity);
//                map.put(physicalEntity.getDbId(), input);
//            } else {
//                input.setCardinality(input.getCardinality() + 1);
//            }
//        }
//        this.input = new HashSet<>(map.values());
//    }
//
////    public void setInput(Set<Input> input) {
////        this.input = input;
////    }
//
//    public List<PhysicalEntity> getOutput() {
//        List<PhysicalEntity> rtn = new ArrayList<>();
//        for (Output aux : output) {
//            for (int i = 0; i < aux.getCardinality(); i++) {
//                rtn.add(aux.getPhysicalEntity());
//            }
//        }
//        return rtn;
//    }
//
//    public void setOutput(List<PhysicalEntity> outputs) {
//        Map<Long, Output> map = new HashMap<>();
//        for (PhysicalEntity physicalEntity : outputs) {
//            Output output = map.get(physicalEntity.getDbId());
//            if (output == null) {
//                output = new Output();
//                output.setEvent(this);
//                output.setPhysicalEntity(physicalEntity);
//                map.put(physicalEntity.getDbId(), output);
//            } else {
//                output.setCardinality(output.getCardinality() + 1);
//            }
//        }
//        this.output = new HashSet<>(map.values());
//    }
//
//    public void setOutput(Set<Output> output) {
//        this.output = output;
//    }

    public List<PhysicalEntity> getEntityOnOtherCell() {
        return entityOnOtherCell;
    }

    public void setEntityOnOtherCell(List<PhysicalEntity> entityOnOtherCell) {
        this.entityOnOtherCell = entityOnOtherCell;
    }

    public List<DatabaseObject> getRequiredInputComponent() {
        return requiredInputComponent;
    }

    public void setRequiredInputComponent(List<DatabaseObject> requiredInputComponent) {
        this.requiredInputComponent = requiredInputComponent;
    }

    public ReactionLikeEvent getHasMember() {
        return hasMember;
    }

    public void setHasMember(ReactionLikeEvent hasMember) {
        this.hasMember = hasMember;
    }

    public List<CatalystActivity> getCatalystActivity() {
        return catalystActivity;
    }

    public void setCatalystActivity(List<CatalystActivity> catalystActivity) {
        this.catalystActivity = catalystActivity;
    }

    public ReactionLikeEvent() {
    }

    public Boolean getIsChimeric() {
        return this.isChimeric;
    }

    public void setIsChimeric(Boolean isChimeric) {
        this.isChimeric = isChimeric;
    }

    public List<ReactionLikeEvent> getNormalReaction() {
        return this.normalReaction;
    }

    public void setNormalReaction(List<ReactionLikeEvent> normalReaction) {
        this.normalReaction = normalReaction;
    }

    public List<EntityFunctionalStatus> getEntityFunctionalStatus() {
        return entityFunctionalStatus;
    }

    public void setEntityFunctionalStatus(List<EntityFunctionalStatus> entityFunctionalStatus) {
        this.entityFunctionalStatus = entityFunctionalStatus;
    }
}
