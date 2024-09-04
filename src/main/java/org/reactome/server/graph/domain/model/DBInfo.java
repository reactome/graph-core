package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;

@Node
public class DBInfo extends Release {

    private String name;
    private Long checksum;

    /**
     * @deprecated
     * Use {@link Release#getReleaseNumber()} instead
     * @return Database version
     */
    @Deprecated
    public Integer getVersion() {
        return this.getReleaseNumber();
    }

    public DBInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getChecksum() {
        return checksum;
    }

    public void setChecksum(Long checksum) {
        this.checksum = checksum;
    }
}
