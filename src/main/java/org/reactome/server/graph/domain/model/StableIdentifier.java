package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeProperty;

public class StableIdentifier extends DatabaseObject {

    @ReactomeProperty
    private String identifier;
    @ReactomeProperty
    private String identifierVersion;
    @ReactomeProperty
    private String oldIdentifier;
    @ReactomeProperty
    private String oldIdentifierVersion;
    @ReactomeProperty
    private Boolean released;
    

    public StableIdentifier() {}

    public StableIdentifier(Long dbId) {
        super(dbId);
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifierVersion() {
        return identifierVersion;
    }

    public void setIdentifierVersion(String identifierVersion) {
        this.identifierVersion = identifierVersion;
    }

    public String getOldIdentifier() {
        return oldIdentifier;
    }

    public void setOldIdentifier(String oldIdentifier) {
        this.oldIdentifier = oldIdentifier;
    }

    public String getOldIdentifierVersion() {
        return oldIdentifierVersion;
    }

    public void setOldIdentifierVersion(String oldIdentifierVersion) {
        this.oldIdentifierVersion = oldIdentifierVersion;
    }

    public Boolean getReleased() {
        return released;
    }

    public void setReleased(Boolean released) {
        this.released = released;
    }


}
