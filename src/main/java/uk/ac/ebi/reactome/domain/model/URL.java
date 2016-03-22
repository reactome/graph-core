package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import uk.ac.ebi.reactome.domain.annotations.ReactomeProperty;

@SuppressWarnings("unused")
@NodeEntity
public class URL extends Publication {

    @ReactomeProperty
    private String uniformResourceLocator;

    public URL() {}

    public String getUniformResourceLocator() {
        return this.uniformResourceLocator;
    }

    public void setUniformResourceLocator(String uniformResourceLocator) {
        this.uniformResourceLocator = uniformResourceLocator;
    }

}
