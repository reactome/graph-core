package org.reactome.server.tools.service.helper;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 06.04.16.
 */
public class PBNode implements Comparable<PBNode> {

    private String stId;
    private String name;
    private String species;
    private String url;
    private String type;
    private Boolean diagram;
    private Boolean unique;

    private Set<PBNode> children;
    private Set<PBNode> parent;

    public void addParent(PBNode node) {
        if (parent==null) {
            parent = new TreeSet<>();
        }
        parent.add(node);
    }
    public void addChild(PBNode node) {
        if (children==null) {
            children = new TreeSet<>();
        }
        children.add(node);
    }

    public Set<PBNode> getLeaves() {
        Set<PBNode> leaves = new TreeSet<>();
        if (this.children == null) {
            leaves.add(this);
        } else {
            for (PBNode child : this.children) {
                leaves.addAll(child.getLeaves());
            }
        }
        return leaves;
    }

    public void print() {
        System.out.println(this.stId);
        if (this.children != null) {
            for (PBNode child : this.children) {
                child.print();
            }
        }
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

    public Set<PBNode> getChildren() {
        return children;
    }

    public void setChildren(Set<PBNode> children) {
        this.children = children;
    }

    public Set<PBNode> getParent() {
        return parent;
    }

    public void setParent(Set<PBNode> parent) {
        this.parent = parent;
    }

    @Override
    public int compareTo(@Nonnull PBNode node) {
        return this.stId.compareTo(node.stId);
    }

}
