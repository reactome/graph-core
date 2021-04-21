package org.reactome.server.graph.domain.result;

import org.neo4j.driver.Value;
import org.reactome.server.graph.domain.model.Pathway;

public class CommonResultDTO extends Pathway {

    private String doi;
    private String speciesName;

    public CommonResultDTO(Value v) {
        setDisplayName(v.get("displayName").asString());
        setStId(v.get("stId").asString());
        setDbId(v.get("dbId").asLong());
        doi = v.get("doi").asString();
        speciesName = v.get("speciesName").asString();
        System.out.println(v.get("nothing_To_find").asString());
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }
}
