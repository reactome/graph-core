package uk.ac.ebi.reactome.repository.placeholder;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.reactome.domain.model.ReferenceGeneProduct;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 19.11.15.
 */
@Repository
public interface ReferenceGeneProductRepository extends GraphRepository<ReferenceGeneProduct> {

    @Query("MATCH (r:ReferenceGeneProduct) WHERE r.dbId IN {0} Return r")
    public Iterable<ReferenceGeneProduct>getProteins(Collection<Long> dbIds);
}
