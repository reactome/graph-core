package uk.ac.ebi.reactome.service.helper;

import javax.annotation.Nonnull;
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
public class Node implements Comparable<Node>{

    private final Class clazz;

    private Set<Node> children;
    private Integer count;

    public Node(Class clazz, Integer count) {
        this.clazz = clazz;
        this.count = count;
        children = Collections.emptySet();
    }

    @SuppressWarnings("unused") // clazz is used in the content service
    public Class getClazz() {
        return clazz;
    }

    public Set<Node> getChildren() {
        return children;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void addChild(Node node) {
        if (children.isEmpty()) {
            children = new TreeSet<>();
        }
        children.add(node);
    }

    @SuppressWarnings("unused")
    public Integer findMaxPage(String className, Integer offset) {
        Node node = iterateTree(this,className);
        if (node != null) {
            return (int) Math.ceil(node.getCount() / (double) offset);
        }
        return 0;
    }

    private Node iterateTree(Node node, String className) {
        if (node.clazz.getSimpleName().equals(className))
            return node;
        Node result = null;
        Iterator iterator = node.getChildren().iterator();
        while(result == null && iterator.hasNext()) {
            result = iterateTree((Node) iterator.next(), className);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return !(clazz != null ? !clazz.equals(node.clazz) : node.clazz != null);

    }

    @Override
    public int hashCode() {
        return clazz != null ? clazz.hashCode() : 0;
    }

    @Override
    public int compareTo(@Nonnull Node node) {
        return this.clazz.getSimpleName().compareTo(node.clazz.getSimpleName());
    }

}
