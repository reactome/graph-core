package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.annotations.StoichiometryView;
import org.reactome.server.graph.domain.relationship.CompositionAggregator;
import org.reactome.server.graph.domain.relationship.Has;
import org.reactome.server.graph.domain.relationship.Input;
import org.reactome.server.graph.domain.relationship.Output;
import org.reactome.server.graph.service.helper.StoichiometryObject;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;
import java.util.stream.Stream;

/**
 * Has four subclasses: Reaction, BlackBoxEvent, Polymerisation and Depolymerisation. All involve the conversion of one or more input molecular entities to an output entity, possibly facilitated by a catalyst.
 * <p>
 * Logic in getter/setter of input and output is needed for retrieving data import using the GKInstance.
 * This is still used for testing if graph and sql produce the same data import
 */
@SuppressWarnings("unused")
@Node
public abstract class ReactionLikeEvent extends Event implements CompositionAggregator {

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

    @Override
    public Stream<? extends Collection<? extends Has<? extends DatabaseObject>>> defineCompositionRelations() {
        return Stream.of(input, output, Has.Util.wrapUniqueElements(catalystActivity, "catalyst"), Has.Util.wrapUniqueElements(regulatedBy, "regulation"));
    }

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
        return Has.Util.simplifiedSort(input);
    }

    @JsonView(StoichiometryView.Flatten.class)
    public List<PhysicalEntity> getInput() {
       return Has.Util.expandStoichiometry(input);
    }

    @JsonView(StoichiometryView.Flatten.class)
    public void setInput(List<PhysicalEntity> inputs) {
        this.input = Has.Util.aggregateStoichiometry(inputs, Input::new);
    }

    @JsonView(StoichiometryView.Nested.class)
    public Set<Input> getInputs() {
        return this.input;
    }

    @JsonView(StoichiometryView.Nested.class)
    public void setInputs(Set<Input> inputs) {
        this.input = inputs;
    }

    @JsonIgnore
    public List<StoichiometryObject> fetchOutput() {
        return Has.Util.simplifiedSort(output);
    }

    public void setOutput(Set<Output> output) {
        this.output = output;
    }

    @JsonView(StoichiometryView.Flatten.class)
    public List<PhysicalEntity> getOutput() {
        return Has.Util.expandStoichiometry(output);
    }

    @JsonView(StoichiometryView.Flatten.class)
    public void setOutput(List<PhysicalEntity> outputs) {
        this.output = Has.Util.aggregateStoichiometry(outputs, Output::new);
    }

    @JsonView(StoichiometryView.Nested.class)
    public Set<Output> getOutputs() {
        return this.output;
    }

    @JsonView(StoichiometryView.Nested.class)
    public void setOutputs(Set<Output> outputs) {
        this.output = outputs;
    }

    @ReactomeSchemaIgnore
    @Override
    public String getClassName() {
        return "Reaction";
    }
}
