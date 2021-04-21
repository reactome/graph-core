package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class DBInfo {

    @JsonIgnore
    @Id
    private Long id;

    private String name;
    private Integer version;
    private Long checksum;

    public DBInfo() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getChecksum() {
        return checksum;
    }

    public void setChecksum(Long checksum) {
        this.checksum = checksum;
    }
}
