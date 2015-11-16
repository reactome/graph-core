package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * SimpleEntity contains fully characterized molecules
 */
@NodeEntity
public class SimpleEntity extends PhysicalEntity {

    @Relationship(type = "HAS_REFERENCE_ENTITY", direction = Relationship.OUTGOING)
    private ReferenceMolecule referenceMolecule;

    public SimpleEntity(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }

    public ReferenceMolecule getReferenceMolecule() {
        return referenceMolecule;
    }

    public void setReferenceMolecule(ReferenceMolecule referenceMolecule) {
        this.referenceMolecule = referenceMolecule;
    }
}
