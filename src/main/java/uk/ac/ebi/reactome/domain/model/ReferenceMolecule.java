package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class ReferenceMolecule extends ReferenceEntity {

    private String atomicConnectivity;
    private String formula;
    private String url;

    public ReferenceMolecule() {
    }

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

    public String getUrl() {
		return url;
	}
    
	public void setUrl(String url) {
		this.url = url;
	}	

}
