package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class ReferenceGroup extends ReferenceEntity {//implements Modification

    private String atomicConnectivity;
    private String formula;

    public ReferenceGroup() {}

    public String getAtomicConnectivity() {
        return atomicConnectivity;
    }

    public void setAtomicConnectivity(String atomicConnectivity) {
        this.atomicConnectivity = atomicConnectivity;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

}
