package uk.ac.ebi.reactome.service.helper;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.02.16.
 */
public class Node implements Comparable<Node>{

    private Set<Node> children;

    private Class clazz;
    private Integer count;

    public Node() {}

    public Node(Class clazz, Integer count) {
        this.clazz = clazz;
        this.count = count;
    }


    public Set<Node> getChildren() {
        return children;
    }

    public void setChildren(Set<Node> children) {
        this.children = children;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void addChild(Node node) {
        if (children == null) {
            children = new TreeSet<>();
        }
        children.add(node);
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
    public int compareTo(Node node) {
        return this.clazz.getSimpleName().compareTo(node.clazz.getSimpleName());
    }

}
