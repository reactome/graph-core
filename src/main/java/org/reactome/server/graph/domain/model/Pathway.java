package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.driver.Value;
import org.reactome.server.graph.domain.ReflectionUtils;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeRelationship;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeTransient;
import org.reactome.server.graph.domain.relationship.HasEncapsulatedEvent;
import org.reactome.server.graph.domain.relationship.HasEvent;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A collection of related Events. These events can be ReactionLikeEvents or Pathways
 */
@SuppressWarnings("unused")
@Node
public class Pathway extends Event {

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

    @Relationship(type = "hasEvent")
    private SortedSet<HasEvent> hasEvent;

    @ReactomeRelationship
    @Relationship(type = "hasEncapsulatedEvent")
    private SortedSet<HasEncapsulatedEvent> hasEncapsulatedEvent;

    @Relationship(type = "normalPathway")
    private Pathway normalPathway;

    public Pathway build(Value v){
        // MAP all field and then build
        List<Field> fields = ReflectionUtils.getAllFields(this.getClass());
        ReflectionUtils.build( v, fields, this);
        return this;
    }

    public Pathway(Value v) {
        Field[] fields = this.getClass().getSuperclass().getSuperclass().getDeclaredFields();
        for (Field field : fields) {
            Value va = v.get(field.getName());
            try {
                field.setAccessible(true);
                if (field.getType().isAssignableFrom(String.class)) {
                    field.set(this, va.asString());
                } else if (field.getType().isAssignableFrom(Long.class)) {
                    field.set(this, va.asLong());
                } else if (field.getType().isAssignableFrom(Integer.class)) {
                    field.set(this, va.asInt());
                } else if (field.getType().isAssignableFrom(Boolean.class)) {
                    field.set(this, va.asBoolean(Boolean.FALSE));
                } else {
                    System.out.println("PLEASE MAP TYPE -> " + field.getType() + " " + field.getName());
                }
            } catch (IllegalAccessException e) {
//                    e.printStackTrace();
            }

        }
//        v.get
        System.out.println("hello");
    }

    public Pathway() {}

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

    public List<Event> getHasEvent() {
        if (hasEvent == null) return null;
        List<Event> rtn = new ArrayList<>();
        for (HasEvent he : hasEvent) {
            rtn.add(he.getEvent());
        }
        return rtn;
    }

    public void setHasEvent(List<Event> hasEvent) {
        this.hasEvent = new TreeSet<>();
        int order = 0;
        for (Event event : hasEvent) {
            HasEvent aux = new HasEvent();
//            aux.setPathway(this);
            aux.setEvent(event);
            aux.setOrder(order++);
            this.hasEvent.add(aux);
        }
    }

    @ReactomeSchemaIgnore
    @JsonIgnore
    public List<Event> getHasEncapsulatedEvent() {
        if (hasEncapsulatedEvent == null) return null;
        List<Event> rtn = new ArrayList<>();
        for (HasEncapsulatedEvent hee : hasEncapsulatedEvent) {
            rtn.add(hee.getEvent());
        }
        return rtn;
    }

    public void setHasEncapsulatedEvent(List<Event> hasEncapsulatedEvent) {
        this.hasEncapsulatedEvent = new TreeSet<>();
        int order = 0;
        for (Event event : hasEncapsulatedEvent) {
            HasEncapsulatedEvent aux = new HasEncapsulatedEvent();
//            aux.setPathway(this);
            aux.setEvent(event);
            aux.setOrder(order++);
            this.hasEncapsulatedEvent.add(aux);
        }
    }

    public Pathway getNormalPathway() {
        return normalPathway;
    }

    public void setNormalPathway(Pathway normalPathway) {
        this.normalPathway = normalPathway;
    }

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "A collection of related Events. These events can be ReactionLikeEvents or Pathways";
    }
}
