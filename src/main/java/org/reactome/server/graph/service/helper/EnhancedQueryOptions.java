package org.reactome.server.graph.service.helper;


public class EnhancedQueryOptions {
    public boolean summariseReferenceEntity = false;
    public boolean includeDisease = false;
    public boolean outgoingOnly = false;

    public EnhancedQueryOptions() {
    }

    public EnhancedQueryOptions(boolean summariseReferenceEntity, boolean includeDisease, boolean outgoingOnly) {
        this.summariseReferenceEntity = summariseReferenceEntity;
        this.includeDisease = includeDisease;
        this.outgoingOnly = outgoingOnly;
    }

    public boolean summarisesReferenceEntity() {
        return summariseReferenceEntity;
    }

    public void setSummariseReferenceEntity(boolean summariseReferenceEntity) {
        this.summariseReferenceEntity = summariseReferenceEntity;
    }

    public boolean includesDisease() {
        return includeDisease;
    }

    public void setIncludeDisease(boolean includeDisease) {
        this.includeDisease = includeDisease;
    }

    public boolean isOutgoingOnly() {
        return outgoingOnly;
    }

    public void setOutgoingOnly(boolean outgoingOnly) {
        this.outgoingOnly = outgoingOnly;
    }
}
