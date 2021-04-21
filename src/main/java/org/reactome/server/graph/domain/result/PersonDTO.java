package org.reactome.server.graph.domain.result;

import org.neo4j.driver.Value;
import org.reactome.server.graph.domain.model.Person;

public class PersonDTO extends Person {

    public PersonDTO(Value p) {
        setDbId(p.get("dbId").asLong());
        setDisplayName(p.get("displayName").asString());
        setFirstname(p.get("firstname").asString(null));
        setSurname(p.get("surname").asString(null));
        setInitial(p.get("initial").asString(null));
        setOrcidId(p.get("orcidId").asString(null));
        setProject(p.get("project").asString(null));
    }
}
