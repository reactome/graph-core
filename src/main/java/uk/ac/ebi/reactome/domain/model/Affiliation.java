package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

import java.util.List;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 17.12.15.
 */
@NodeEntity
public class Affiliation extends  DatabaseObject{

    private String text;
    private List<String> name;

    public Affiliation() {}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }
}
