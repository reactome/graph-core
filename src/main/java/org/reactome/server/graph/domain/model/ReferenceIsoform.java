package org.reactome.server.graph.domain.model;

import java.util.List;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@SuppressWarnings("unused")
@Node
public class ReferenceIsoform extends ReferenceGeneProduct {
    private static final long serialVersionUID = 1L;

    @ReactomeProperty
    private String variantIdentifier;

    @Relationship(type = "isoformParent")
    private List<ReferenceGeneProduct> isoformParent;

    public ReferenceIsoform() {
        super();
    }

    public String getVariantIdentifier() {
        return this.variantIdentifier;
    }

    public void setVariantIdentifier(String variantIdentifier) {
        this.variantIdentifier = variantIdentifier;
    }

    public List<ReferenceGeneProduct> getIsoformParent() {
        return isoformParent;
    }

    public void setIsoformParent(List<ReferenceGeneProduct> isoformParent) {
        this.isoformParent = isoformParent;
    }
}
