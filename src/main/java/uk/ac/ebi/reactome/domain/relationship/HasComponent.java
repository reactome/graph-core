package uk.ac.ebi.reactome.domain.relationship;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import uk.ac.ebi.reactome.domain.model.Complex;
import uk.ac.ebi.reactome.domain.model.PhysicalEntity;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * HasComponent is the relationship entity of Complexes. It is needed to specify the cardinality (stoichiometry) of
 * components.
 */
@RelationshipEntity(type = "HAS_COMPONENT")
public class HasComponent {

    @SuppressWarnings("unused")
    @GraphId
    private Long id;

    private Integer cardinality;

    @StartNode
    private Complex complex;
    @EndNode
    private PhysicalEntity physicalEntity;


    public HasComponent() {}

    public Integer getCardinality() {
        return cardinality;
    }

    @SuppressWarnings("unused")
    public void setCardinality(Integer cardinality) {
        this.cardinality = cardinality;
    }

    public PhysicalEntity getComplex() {
        return complex;
    }

    @SuppressWarnings("unused")
    public void setComplex(Complex complex) {
        this.complex = complex;
    }

    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    @SuppressWarnings("unused")
    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }

}
