package uk.ac.ebi.reactome.repository.placeholder;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.reactome.domain.model.Complex;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
@Repository
public interface ComplexRepository extends GraphRepository<Complex> {
}
