package org.reactome.server.tools.domain.result;

import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 18.04.16.
 */
@QueryResult
public class ComponentOf {

    private String type;
    private List<String> names;
    private List<String> stIds;

    public ComponentOf() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getStIds() {
        return stIds;
    }

    public void setStIds(List<String> stIds) {
        this.stIds = stIds;
    }
}
