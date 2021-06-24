package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.Person;
import org.reactome.server.graph.domain.model.Publication;
import org.reactome.server.graph.domain.result.PersonAuthorReviewer;
import org.reactome.server.graph.domain.result.SimpleEventProjection;
import org.reactome.server.graph.repository.EventRepository;
import org.reactome.server.graph.repository.PersonAuthorReviewerRepository;
import org.reactome.server.graph.repository.PersonRepository;
import org.reactome.server.graph.repository.PublicationRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    private final PublicationRepository publicationRepository;

    private final EventRepository eventRepository;

    private final PersonAuthorReviewerRepository personAuthorReviewerRepository;

    public PersonService(PersonRepository personRepository, PublicationRepository publicationRepository, EventRepository eventRepository, PersonAuthorReviewerRepository personAuthorReviewerRepository) {
        this.personRepository = personRepository;
        this.publicationRepository = publicationRepository;
        this.eventRepository = eventRepository;
        this.personAuthorReviewerRepository = personAuthorReviewerRepository;
    }

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
            return publicationRepository.getPublicationsOfPersonByEmail(id);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return publicationRepository.getPublicationsOfPersonByDbId(Long.valueOf(id));
        } else if (DatabaseObjectUtils.isOrcidId(id)){
            return publicationRepository.getPublicationsOfPersonByOrcidId(id);
        } else {
            return null;
        }
    }

    public Collection<SimpleEventProjection> getAuthoredPathways(Object identifier) {
        String id = identifier.toString();
        if (DatabaseObjectUtils.isDbId(id)) {
            return eventRepository.getAuthoredPathwaysByDbId(Long.valueOf(id));
        } else if (DatabaseObjectUtils.isOrcidId(id)){
            return eventRepository.getAuthoredPathwaysByOrcidId(id);
        } else {
            return null;
        }
    }

    public Collection<SimpleEventProjection> getAuthoredReactions(Object identifier) {
        String id = identifier.toString();
        if (DatabaseObjectUtils.isDbId(id)) {
            return eventRepository.getAuthoredReactionsByDbId(Long.valueOf(id));
        } else if (DatabaseObjectUtils.isOrcidId(id)){
            return eventRepository.getAuthoredReactionsByOrcidId(id);
        } else {
            return null;
        }
    }

    public Collection<SimpleEventProjection> getReviewedPathways(Object identifier) {
        String id = identifier.toString();
        if (DatabaseObjectUtils.isDbId(id)) {
            return eventRepository.getReviewedPathwaysByDbId(Long.valueOf(id));
        } else if (DatabaseObjectUtils.isOrcidId(id)){
            return eventRepository.getReviewedPathwaysByOrcidId(id);
        } else {
            return null;
        }
    }

    public Collection<SimpleEventProjection> getReviewedReactions(Object identifier) {
        String id = identifier.toString();
        if (DatabaseObjectUtils.isDbId(id)) {
            return eventRepository.getReviewedReactionsByDbId(Long.valueOf(id));
        } else if (DatabaseObjectUtils.isOrcidId(id)){
            return eventRepository.getReviewedReactionsByOrcidId(id);
        } else {
            return null;
        }
    }

    public Collection<PersonAuthorReviewer> getAuthorsReviewers(){
        return personAuthorReviewerRepository.getAuthorsReviewers();
    }

}
