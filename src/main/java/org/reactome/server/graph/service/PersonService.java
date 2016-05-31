package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.Person;
import org.reactome.server.graph.domain.model.Publication;
import org.reactome.server.graph.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 31.05.16.
 */
@SuppressWarnings("WeakerAccess")
@Service
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

    public Person findPersonByOrcidId(String orcidId) {
        return personRepository.findPersonByOrcidId(orcidId);
    }

    public Person findPersonByDbId(Long dbId) {
        return personRepository.findPersonByDbId(dbId);
    }

    public Collection<Publication> getPublicationsOfPersonByOrcidId(String orcidId) {
        return personRepository.getPublicationsOfPersonByOrcidId(orcidId);
    }

    public Collection<Publication> getPublicationsOfPersonByDbId(Long dbId) {
        return personRepository.getPublicationsOfPersonByDbId(dbId);
    }

    public Collection<Pathway> getAuthoredPathwaysByOrcidId(String orcidId) {
        return personRepository.getAuthoredPathwaysByOrcidId(orcidId);
    }

    public Collection<Pathway> getAuthoredPathwaysByDbId(Long dbId) {
        return personRepository.getAuthoredPathwaysByDbId(dbId);
    }
}
