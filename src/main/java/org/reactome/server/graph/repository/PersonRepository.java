package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.Person;
import org.reactome.server.graph.domain.model.Publication;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 31.05.16.
 */
@Repository
public interface PersonRepository extends GraphRepository<Person>{

    @Query("Match (n:Person) Where  n.surname in {0} AND n.firstname in {0} RETURN n")
    Collection<Person> findPersonByName(String[] name);

    @Query("Match (n:Person) Where  n.surname in {0} OR n.firstname in {0} RETURN n")
    Collection<Person> queryPersonByName(String[] name);

    @Query("MATCH (n:Person{orcidId:{0}}) Return n")
    Person findPersonByOrcidId(String orcidId);

    @Query("Match (n:Person{dbId:{0}}) Return n")
    Person findPersonByDbId(Long dbId);

    @Query("Match (n:Person{eMailAddress:{0}}) Return n")
    Person findPersonByEmail(String email);

    @Query("MATCH (n:Person{orcidId:{0}})-[:author]-(m:Publication) Return m")
    Collection<Publication> getPublicationsOfPersonByOrcidId(String orcidId);

    @Query("MATCH (n:Person{dbId:{0}})-[:author]-(m:Publication) Return m")
    Collection<Publication> getPublicationsOfPersonByDbId(Long dbId);

    @Query("MATCH (n:Person{eMailAddress:{0}})-[:author]-(m:Publication) Return m")
    Collection<Publication> getPublicationsOfPersonByEmail(String email);

    @Query("Match (n:Person{orcidId:{0}})-[r:author]->(m:InstanceEdit)-[e:authored]->(k:Pathway) RETURN k")
    Collection<Pathway> getAuthoredPathwaysByOrcidId(String orcidId);

    @Query("Match (n:Person{dbId:{0}})-[r:author]->(m:InstanceEdit)-[e:authored]->(k:Pathway) RETURN k")
    Collection<Pathway> getAuthoredPathwaysByDbId(Long dbId);

    @Query("Match (n:Person{eMailAddress:{0}})-[r:author]->(m:InstanceEdit)-[e:authored]->(k:Pathway) RETURN k")
    Collection<Pathway> getAuthoredPathwaysByEmail(String email);
}
