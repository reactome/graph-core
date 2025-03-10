package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.reactome.server.graph.domain.annotations.*;
import org.reactome.server.graph.domain.relationship.CompositionAggregator;
import org.reactome.server.graph.domain.relationship.Has;
import org.reactome.server.graph.domain.relationship.HasEncapsulatedEvent;
import org.reactome.server.graph.domain.relationship.HasEvent;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.management.relation.Relation;
import java.util.*;
import java.util.stream.Stream;

/**
 * A collection of related Events. These events can be ReactionLikeEvents or Pathways
 */
@SuppressWarnings("unused")
@Node
public class Pathway extends Event implements CompositionAggregator {

    @ReactomeProperty
    private String doi;

    @ReactomeProperty(addedField = true)
    private Boolean hasDiagram;

    @ReactomeProperty
    private Boolean hasEHLD = false;

    @ReactomeTransient
    private Integer diagramWidth;
    @ReactomeTransient
    private Integer diagramHeight;

    @ReactomeProperty
    private String isCanonical;

    @ReactomeProperty
    private String lastUpdatedDate;

    @Relationship(type = Relationships.HAS_EVENT)
    private SortedSet<HasEvent> hasEvent;

    @ReactomeRelationship(addedField = true)
    @Relationship(type = Relationships.HAS_ENCAPSULATED_EVENT)
    private SortedSet<HasEncapsulatedEvent> hasEncapsulatedEvent;

    @Relationship(type = Relationships.NORMAL_PATHWAY)
    private Pathway normalPathway;

    @Relationship(type = Relationships.NORMAL_PATHWAY, direction = Relationship.Direction.INCOMING)
    private List<Pathway> diseasePathways;

    @Override
    public Stream<? extends Collection<? extends Has<? extends DatabaseObject>>> defineCompositionRelations() {
        return Stream.of(hasEvent, hasEncapsulatedEvent);
    }

    public Pathway() {
    }

    public Pathway(Long dbId) {
        super(dbId);
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public Boolean getHasDiagram() {
        return hasDiagram;
    }

    public void setHasDiagram(Boolean hasDiagram) {
        this.hasDiagram = hasDiagram;
    }

    public Boolean getHasEHLD() {
        return hasEHLD;
    }

    public void setHasEHLD(Boolean hasEHLD) {
        this.hasEHLD = hasEHLD;
    }

    @JsonIgnore
    @ReactomeSchemaIgnore
    public Integer getDiagramWidth() {
        return diagramWidth;
    }

    public void setDiagramWidth(Integer diagramWidth) {
        this.diagramWidth = diagramWidth;
    }

    @JsonIgnore
    @ReactomeSchemaIgnore
    public Integer getDiagramHeight() {
        return diagramHeight;
    }

    public void setDiagramHeight(Integer diagramHeight) {
        this.diagramHeight = diagramHeight;
    }

    public String getIsCanonical() {
        return isCanonical;
    }

    public void setIsCanonical(String isCanonical) {
        this.isCanonical = isCanonical;
    }

    @ReactomeSchemaIgnore
    @JsonView(StoichiometryView.Nested.class)
    public SortedSet<HasEvent> getEvents() {
        return hasEvent;
    }

    @JsonView(StoichiometryView.Nested.class)
    public void setEvents(SortedSet<HasEvent> hasEvent) {
        this.hasEvent = hasEvent;
    }

    @JsonView(StoichiometryView.Flatten.class)
    public List<Event> getHasEvent() {
        return Has.Util.expandStoichiometry(hasEvent);
    }

    @JsonView(StoichiometryView.Flatten.class)
    public void setHasEvent(List<Event> hasEvent) {
        this.hasEvent = Has.Util.aggregateStoichiometry(hasEvent, HasEvent::new);
    }

    @ReactomeSchemaIgnore
    @JsonIgnore
    public List<Event> getHasEncapsulatedEvent() {
        return Has.Util.expandStoichiometry(hasEncapsulatedEvent);
    }

    public void setHasEncapsulatedEvent(List<Event> hasEncapsulatedEvent) {
        this.hasEncapsulatedEvent = Has.Util.aggregateStoichiometry(hasEncapsulatedEvent, HasEncapsulatedEvent::new);
    }

    public Pathway getNormalPathway() {
        return normalPathway;
    }

    public void setNormalPathway(Pathway normalPathway) {
        this.normalPathway = normalPathway;
    }

    public List<Pathway> getDiseasePathways() {
        return diseasePathways;
    }

    public void setDiseasePathways(List<Pathway> diseasePathways) {
        this.diseasePathways = diseasePathways;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "A collection of related Events. These events can be ReactionLikeEvents or Pathways";
    }
}
