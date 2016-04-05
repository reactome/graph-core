package org.reactome.server.tools.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.reactome.server.tools.domain.annotations.ReactomeProperty;

@SuppressWarnings("unused")
@NodeEntity
public class FragmentReplacedModification extends FragmentModification {

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
