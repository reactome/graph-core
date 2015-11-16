package uk.ac.ebi.reactome.service.placeholder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Service;
import uk.ac.ebi.reactome.domain.model.Complex;
import uk.ac.ebi.reactome.repository.placeholder.ComplexRepository;
import uk.ac.ebi.reactome.service.ServiceImpl;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
@Service
public class ComplexServiceImpl extends ServiceImpl<Complex> implements ComplexService {

    @Autowired
    private ComplexRepository complexRepository;

    @Override
    public GraphRepository<Complex> getRepository() {
        return complexRepository;
    }
}
