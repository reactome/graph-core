package org.reactome.server.graph.domain.schema;

import com.fasterxml.jackson.annotation.JsonGetter;

public class SchemaDataCatalog {

    private static final String URL = "https://reactome.org";
    private static final String NAME = "Reactome";

    public String getUrl() {
        return URL;
    }

    public String getName() {
        return NAME;
    }

    @JsonGetter(value = "@type")
    public String getType(){
        return "DataCatalog";
    }
}
