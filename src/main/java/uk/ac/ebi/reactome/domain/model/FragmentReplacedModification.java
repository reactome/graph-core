package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class FragmentReplacedModification extends FragmentModification {

    private String alteredAminoAcidFragment;
    
    public FragmentReplacedModification() {
    }

    public String getAlteredAminoAcidFragment() {
        return alteredAminoAcidFragment;
    }

    public void setAlteredAminoAcidFragment(String alteredAminoAcidFragment) {
        this.alteredAminoAcidFragment = alteredAminoAcidFragment;
    }
    
    
    
}
