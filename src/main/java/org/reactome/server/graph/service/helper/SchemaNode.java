package org.reactome.server.graph.service.helper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.02.16.
 */
@JsonPropertyOrder({"className", "count", "children"})
public class SchemaNode implements Comparable<SchemaNode>{

    private final Class clazz;

    private Set<SchemaNode> children;
    private Integer count;

    public SchemaNode(Class clazz, Integer count) {
        this.clazz = clazz;
        this.count = count;
        children = Collections.emptySet();
    }

    @SuppressWarnings("unused") // clazz is used in the content service
    @JsonIgnore
    public Class getClazz() {
        return clazz;
    }

    public String getClassName() {
        return clazz.getSimpleName();
    }

    public Set<SchemaNode> getChildren() {
        return children;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void addChild(SchemaNode node) {
        if (children.isEmpty()) {
            children = new TreeSet<>();
        }
        children.add(node);
    }

    @SuppressWarnings("unused")
    public Integer findMaxPage(String className, Integer offset) {
        SchemaNode node = iterateTree(this,className);
        if (node != null) {
            return (int) Math.ceil(node.getCount() / (double) offset);
        }
        return 0;
    }

    private SchemaNode iterateTree(SchemaNode node, String className) {
        if (node.clazz.getSimpleName().equals(className))
            return node;
        SchemaNode result = null;
        Iterator iterator = node.getChildren().iterator();
        while(result == null && iterator.hasNext()) {
            result = iterateTree((SchemaNode) iterator.next(), className);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchemaNode node = (SchemaNode) o;

        return !(clazz != null ? !clazz.equals(node.clazz) : node.clazz != null);

    }

    @Override
    public int hashCode() {
        return clazz != null ? clazz.hashCode() : 0;
    }

    @Override
    public int compareTo(@NonNull SchemaNode node) {
        return this.clazz.getSimpleName().compareTo(node.clazz.getSimpleName());
    }

}
