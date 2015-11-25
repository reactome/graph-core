package uk.ac.ebi.reactome.repository.placeholder;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.reactome.domain.result.SomeResult;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 18.11.15.
 */
@Repository
public interface SomeResultRepository extends GraphRepository<SomeResult> {

    @Query ("MATCH (n:Pathway{dbId:{0}})-[*]->(m:ReferenceEntity) RETURN n.dbId as pathway,collect(m.dbId) as entitySet, COUNT( m) as count")
    SomeResult getResult(Long dbId);


}
