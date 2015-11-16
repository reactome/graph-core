package uk.ac.ebi.reactome.domain.model;


import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.domain.relationship.Interaction;

import java.util.Set;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 *
 */
@NodeEntity
public class Interactor {

    @GraphId
    private Long id;

    private String intactId;
    private String name;

    @Relationship(type = "INTERACTS_WITH", direction = Relationship.UNDIRECTED)
    private Set<Interaction> interactions;

    public Interactor(String intactId, String name) {
        this.intactId = intactId;
        this.name = name;
    }

    public String getIntactId() {
        return intactId;
    }

    public void setIntactId(String intactId) {
        this.intactId = intactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(Set<Interaction> interactions) {
        this.interactions = interactions;
    }
}
