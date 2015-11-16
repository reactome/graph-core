package uk.ac.ebi.reactome.domain.relationship;

import org.neo4j.ogm.annotation.*;
import uk.ac.ebi.reactome.domain.model.Interactor;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 *
 */
@RelationshipEntity(type = "INTERACTS_WITH")
public class Interaction {

    @GraphId
    private Long id;

    @Property
    private Double score;

    @StartNode
    private Interactor interactorA;
    @EndNode
    private Interactor interactorB;

    public Interaction() {}

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Interactor getComplex() {
        return interactorA;
    }

    public void setComplex(Interactor interactorA) {
        this.interactorA = interactorA;
    }

    public Interactor getPhysicalEntity() {
        return interactorB;
    }

    public void setPhysicalEntity(Interactor interactorB) {
        this.interactorB = interactorB;
    }
}
