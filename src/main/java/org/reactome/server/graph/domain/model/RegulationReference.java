package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

//NB by GW: I am not sure why the original code needs to use regulatedBy, intead of regulation. To make
// it compatible with the original code and also be matched with the data model, I will keep both sets
// of getter and setter. 
@Node
public class RegulationReference extends ControlReference {

    @Relationship(type = "regulation")
    private Regulation regulation;

    public RegulationReference() {
    }

    public Regulation getRegulatedBy() {
        return regulation;
    }

    public void setRegulatedBy(Regulation regulation) {
        this.regulation = regulation;
    }

    public Regulation getRegulation() {
        return regulation;
    }

    public void setRegulation(Regulation regulation) {
        this.regulation = regulation;
    }
    
    
}
