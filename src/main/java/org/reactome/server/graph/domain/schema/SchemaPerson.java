package org.reactome.server.graph.domain.schema;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.reactome.server.graph.domain.model.Affiliation;
import org.reactome.server.graph.domain.model.Person;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class SchemaPerson extends SchemaCreator {

    private final String givenName;
    private final String familyName;
    private final List<SchemaOrganization> affiliation = new ArrayList<>();
    private final String url; //For the orcidId

    SchemaPerson(Person person) {
        this.givenName = person.getFirstname();
        this.familyName = person.getSurname();
        if(person.getAffiliation()!=null) {
            for (Affiliation a : person.getAffiliation()) {
                this.affiliation.add(new SchemaOrganization(a));
            }
        }
        url = "http://europepmc.org/authors/" + person.getOrcidId();
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public List<SchemaOrganization> getAffiliation() {
        return affiliation;
    }

    public String getUrl() {
        return url;
    }

    @JsonGetter(value = "@type")
    public String getType(){
        return "Person";
    }
}
