package org.reactome.server.graph.service.helper;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 06.04.16.
 */
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="stId")
public class PathwayBrowserNode implements Comparable<PathwayBrowserNode> {

    private String stId;
    private String name;
    private String species;
    @JsonIgnore
    private String url;
    private String type;
    private Boolean diagram;
    @JsonIgnore
    private Boolean unique;

    private Set<PathwayBrowserNode> children;
    private Set<PathwayBrowserNode> parent;

    @JsonIgnore
    private boolean highlighted = false;

    @JsonIgnore
    private boolean clickable = false;

    public void addParent(PathwayBrowserNode node) {
        if (parent==null) {
            parent = new TreeSet<>();
        }
        parent.add(node);
    }
    public void addChild(PathwayBrowserNode node) {
        if (children==null) {
            children = new TreeSet<>();
        }
        children.add(node);
    }

    @JsonIgnore
    public Set<PathwayBrowserNode> getLeaves() {
        Set<PathwayBrowserNode> leaves = new TreeSet<>();
        if (this.children == null) {
            leaves.add(this);
        } else {
            for (PathwayBrowserNode child : this.children) {
                leaves.addAll(child.getLeaves());
            }
        }
        return leaves;
    }

    public Boolean hasDiagram() {
        if (diagram ==null) {
            return false;
        } else {
            return diagram;
        }

    }

    public Boolean isUnique() {
        return unique;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getDiagram() {
        return diagram;
    }

    public void setDiagram(Boolean diagram) {
        this.diagram = diagram;
    }

    public Boolean getUnique() {
        return unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    public Set<PathwayBrowserNode> getChildren() {
        return children;
    }

    public void setChildren(Set<PathwayBrowserNode> children) {
        this.children = children;
    }

    public Set<PathwayBrowserNode> getParent() {
        return parent;
    }

    public void setParent(Set<PathwayBrowserNode> parent) {
        this.parent = parent;
    }

    public boolean getHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    @Override
    public int compareTo(@Nonnull PathwayBrowserNode node) {
        return this.stId.compareTo(node.stId);
    }

}
