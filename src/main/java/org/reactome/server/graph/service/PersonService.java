package org.reactome.server.graph.service;

import org.apache.commons.lang.StringUtils;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.Person;
import org.reactome.server.graph.domain.model.Publication;
import org.reactome.server.graph.repository.PersonRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 31.05.16.
 */
@Service
@SuppressWarnings("WeakerAccess")
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    //    equals person name
    public Collection<Person> findPersonByName(String name) {
        name = "(?i)" + name;
        return personRepository.findPersonByName(name);
    }

    //    contains person name
    public Collection<Person> queryPersonByName(String name) {
        name = ".*(?i)" + name + ".*";
        return personRepository.findPersonByName(name);
    }

    public Person findPerson(Object identifier) {
        String id = identifier.toString();
        if (DatabaseObjectUtils.isDbId(id)) {
            return personRepository.findPersonByDbId(Long.valueOf(id));
        } else {
            return personRepository.findPersonByOrcidId(id);
        }
    }

    public Collection<Publication> getPublicationsOfPerson(Object identifier) {
        String id = identifier.toString();
        if (DatabaseObjectUtils.isDbId(id)) {
            return personRepository.getPublicationsOfPersonByDbId(Long.valueOf(id));
        } else {
            return personRepository.getPublicationsOfPersonByOrcidId(id);
        }
    }

    public Collection<Pathway> getAuthoredPathways(Object identifier) {
        String id = identifier.toString();
        if (DatabaseObjectUtils.isDbId(id)) {
            return personRepository.getAuthoredPathwaysByDbId(Long.valueOf(id));
        } else {
            return personRepository.getAuthoredPathwaysByOrcidId(id);
        }
    }

}
