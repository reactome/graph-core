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
@RelationshipEntity(type = "hasComponent")
public class HasComponent {

    @SuppressWarnings("unused")
    @GraphId
    private Long id;

    private Integer cardinality = 1;

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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        HasComponent component = (HasComponent) o;
//
//        if (complex != null ? !complex.equals(component.complex) : component.complex != null) return false;
//        return !(physicalEntity != null ? !physicalEntity.equals(component.physicalEntity) : component.physicalEntity != null);
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = complex != null ? complex.hashCode() : 0;
//        result = 31 * result + (physicalEntity != null ? physicalEntity.hashCode() : 0);
//        return result;
//    }
}
