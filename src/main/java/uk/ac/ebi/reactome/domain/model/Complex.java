package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.domain.relationship.HasComponent;

import java.util.Set;


/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * Complex represents a "complex" of two or more PhysicalEntities
 */
@NodeEntity
public class Complex extends PhysicalEntity {

    @Relationship(type = "HAS_COMPONENT", direction = Relationship.OUTGOING)
    private Set<HasComponent>components;

    public Complex() {}

    public Complex(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }

    public Set<HasComponent> getComponents() {
        return components;
    }

    @SuppressWarnings("unused")
    public void setComponents(Set<HasComponent> components) {
        this.components = components;
    }
}
