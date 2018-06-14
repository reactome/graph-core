package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.Person;
import org.reactome.server.graph.domain.model.Publication;
import org.reactome.server.graph.domain.result.PersonAuthorReviewer;
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
        String[] names = name.split(" ");
        for (int i = 0; i<names.length; i++) {
            names[i] = names[i].substring(0, 1).toUpperCase() + names[i].substring(1);
        }
        return personRepository.findPersonByName(names);
    }

    //    contains person name
    public Collection<Person> queryPersonByName(String name) {
        String[] names = name.split(" ");
        for (int i = 0; i<names.length; i++) {
            names[i] = names[i].substring(0, 1).toUpperCase() + names[i].substring(1);
        }
        return personRepository.queryPersonByName(names);
    }

    public Person findPerson(Object identifier) {
        String id = identifier.toString();
        if (DatabaseObjectUtils.isDbId(id)) {
            return personRepository.findPersonByDbId(Long.valueOf(id));
        } else if (DatabaseObjectUtils.isOrcidId(id)){
            return personRepository.findPersonByOrcidId(id);
        } else {
            return null;
        }
    }

    @Deprecated
    public Collection<Publication> getPublicationsOfPerson(Object identifier) {
        String id = identifier.toString();
        if (DatabaseObjectUtils.isEmail(id)){
            return personRepository.getPublicationsOfPersonByEmail(id);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return personRepository.getPublicationsOfPersonByDbId(Long.valueOf(id));
        } else if (DatabaseObjectUtils.isOrcidId(id)){
            return personRepository.getPublicationsOfPersonByOrcidId(id);
        } else {
            return null;
        }
    }

    public Collection<Pathway> getAuthoredPathways(Object identifier) {
        String id = identifier.toString();
        if (DatabaseObjectUtils.isDbId(id)) {
            return personRepository.getAuthoredPathwaysByDbId(Long.valueOf(id));
        } else if (DatabaseObjectUtils.isOrcidId(id)){
            return personRepository.getAuthoredPathwaysByOrcidId(id);
        } else {
            return null;
        }
    }

    public Collection<PersonAuthorReviewer> getAuthorsReviewers(){
        return personRepository.getAuthorsReviewers();
    }

}
