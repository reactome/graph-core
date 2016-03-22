package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.domain.annotations.ReactomeProperty;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class DatabaseIdentifier extends DatabaseObject {

    @ReactomeProperty
    private String identifier;
    @ReactomeProperty
    private String url;

    @Relationship(type = "crossReference", direction = Relationship.OUTGOING)
    private List<DatabaseIdentifier> crossReference;

    @Relationship(type = "referenceDatabase", direction = Relationship.OUTGOING)
    private ReferenceDatabase referenceDatabase;

    public DatabaseIdentifier() {}

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<DatabaseIdentifier> getCrossReference() {
        return crossReference;
    }

    public void setCrossReference(List<DatabaseIdentifier> crossReference) {
        this.crossReference = crossReference;
    }

    public ReferenceDatabase getReferenceDatabase() {
        return referenceDatabase;
    }

    public void setReferenceDatabase(ReferenceDatabase referenceDatabase) {
        this.referenceDatabase = referenceDatabase;
    }
}
