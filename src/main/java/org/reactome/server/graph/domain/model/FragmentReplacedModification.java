package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;

@SuppressWarnings("unused")
@Node
public class FragmentReplacedModification extends FragmentModification {
    private static final long serialVersionUID = 1L;

    @ReactomeProperty
    private String alteredAminoAcidFragment;

    public FragmentReplacedModification() {}

    public String getAlteredAminoAcidFragment() {
        return alteredAminoAcidFragment;
    }

    public void setAlteredAminoAcidFragment(String alteredAminoAcidFragment) {
        this.alteredAminoAcidFragment = alteredAminoAcidFragment;
    }
}
