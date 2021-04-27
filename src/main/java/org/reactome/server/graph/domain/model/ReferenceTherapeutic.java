package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;

@Node
public class ReferenceTherapeutic extends ReferenceEntity{

    @ReactomeProperty
    private String abbreviation;
    @ReactomeProperty
    private List<String> approvalSource;
    @ReactomeProperty
    private Boolean approved;
    @ReactomeProperty
    private String inn;
    @ReactomeProperty
    private String type;

    public ReferenceTherapeutic() {
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public List<String> getApprovalSource() {
        return approvalSource;
    }

    public void setApprovalSource(List<String> approvalSource) {
        this.approvalSource = approvalSource;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
