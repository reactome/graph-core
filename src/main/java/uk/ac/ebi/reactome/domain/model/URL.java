package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class URL extends Publication {

    private String uniformResourceLocator;

    public URL() {}

    public String getUniformResourceLocator() {
        return this.uniformResourceLocator;
    }

    public void setUniformResourceLocator(String uniformResourceLocator) {
        this.uniformResourceLocator = uniformResourceLocator;
    }

}
