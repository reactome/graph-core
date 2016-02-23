package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@SuppressWarnings("unused")
@NodeEntity
public class TranslationalModification extends AbstractModifiedResidue {

    private Integer coordinate;

    @Relationship(type = "psiMod", direction = Relationship.OUTGOING)
    private PsiMod psiMod;
    
    public TranslationalModification() {}

    public Integer getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Integer coordinate) {
        this.coordinate = coordinate;
    }

    public PsiMod getPsiMod() {
        return psiMod;
    }

    public void setPsiMod(PsiMod psiMod) {
        this.psiMod = psiMod;
    }
    
}
