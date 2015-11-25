package uk.ac.ebi.reactome.service.placeholder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.reactome.domain.result.SomeResult;
import uk.ac.ebi.reactome.repository.placeholder.SomeResultRepository;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 18.11.15.
 */
@Service
public class SomeResultService {

    @Autowired
    private SomeResultRepository someResultRepository;

    public SomeResult getSomeResult(Long dbId){
        return someResultRepository.getResult(dbId);
    }
}
