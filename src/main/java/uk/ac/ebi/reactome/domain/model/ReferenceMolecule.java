package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

@SuppressWarnings("unused")
@NodeEntity
public class ReferenceMolecule extends ReferenceEntity {

    private String atomicConnectivity;
    private String formula;

    public ReferenceMolecule() {}

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
