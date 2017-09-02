package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class ReplacedResidue extends GeneticallyModifiedResidue {

    @ReactomeProperty
    private Integer coordinate;

    @Relationship(type = "psiMod")
    private List<PsiMod> psiMod;
    
    public ReplacedResidue() {}

    public Integer getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Integer coordinate) {
        this.coordinate = coordinate;
    }

    public List<PsiMod> getPsiMod() {
        return psiMod;
    }

    @Relationship(type = "psiMod")
    public void setPsiMod(List<PsiMod> psiMod) {
        this.psiMod = psiMod;
    }
    
    
}
