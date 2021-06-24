package org.reactome.server.graph.domain.relationship;

public abstract class AbstractRelationship implements Comparable<AbstractRelationship> {
    protected Integer stoichiometry = 1;
    protected int order;

    public Integer getStoichiometry() {
        return stoichiometry;
    }

    public void setStoichiometry(Integer stoichiometry) {
        this.stoichiometry = stoichiometry;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int compareTo(AbstractRelationship o) {
        return this.order - o.order;
    }
}
