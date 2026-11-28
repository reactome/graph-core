package org.reactome.server.graph.domain.model;

import java.util.List;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@SuppressWarnings("unused")
@Node
public class ReplacedResidue extends GeneticallyModifiedResidue {
    private static final long serialVersionUID = 1L;

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

    public void setPsiMod(List<PsiMod> psiMod) {
        this.psiMod = psiMod;
    }
}
