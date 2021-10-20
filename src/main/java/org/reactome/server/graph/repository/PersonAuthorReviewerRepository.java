package org.reactome.server.graph.repository;

import org.neo4j.driver.types.MapAccessor;
import org.neo4j.driver.types.TypeSystem;
import org.reactome.server.graph.domain.model.Person;
import org.reactome.server.graph.domain.result.PersonAuthorReviewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.function.BiFunction;

@Repository
public class PersonAuthorReviewerRepository {

    private final Neo4jClient neo4jClient;
    private final Neo4jMappingContext neo4jMappingContext;

    @Value("${spring.data.neo4j.database:graph.db}")
    private String databaseName;

    @Autowired
    public PersonAuthorReviewerRepository(Neo4jClient neo4jClient, Neo4jMappingContext neo4jMappingContext) {
        this.neo4jClient = neo4jClient;
        this.neo4jMappingContext = neo4jMappingContext;
    }

    public Collection<PersonAuthorReviewer> getAuthorsReviewers() {
        String query = " " +
                "MATCH (per:Person)-[:author]->(ie:InstanceEdit) " +
                "WITH DISTINCT per, ie " +
                "OPTIONAL MATCH (ie)-[:authored]->(ap:Pathway) " +
                "OPTIONAL MATCH (ie)-[:authored]->(ar:ReactionLikeEvent) " +
                "OPTIONAL MATCH (ie)-[:reviewed]->(rp:Pathway) " +
                "OPTIONAL MATCH (ie)-[:reviewed]->(rr:ReactionLikeEvent) " +
                "WITH DISTINCT per, SIZE(COLLECT(DISTINCT ap)) AS aps, SIZE(COLLECT(DISTINCT ar)) AS ars, SIZE(COLLECT(DISTINCT rp)) AS rps, SIZE(COLLECT(DISTINCT rr)) AS rrs " +
                "WHERE aps > 0 OR ars > 0 OR rps > 0 OR rrs > 0 " +
                "RETURN per AS person, aps AS authoredPathways, rps AS reviewedPathways, ars AS authoredReactions, rrs AS reviewedReactions";

        BiFunction<TypeSystem, MapAccessor, Person> mappingFunction = neo4jMappingContext.getRequiredMappingFunctionFor(Person.class);
        Collection<PersonAuthorReviewer> wrapper = neo4jClient.query(query).in(databaseName).fetchAs(PersonAuthorReviewer.class)
                .mappedBy((typeSystem, record) -> {
                    Person n = mappingFunction.apply(typeSystem, record.get("person"));
                    return new PersonAuthorReviewer(n, record.get("authoredPathways").asLong(),record.get("reviewedPathways").asLong(),record.get("authoredReactions").asLong(),record.get("reviewedReactions").asLong());
                }).all();

        return wrapper;
    }
}
