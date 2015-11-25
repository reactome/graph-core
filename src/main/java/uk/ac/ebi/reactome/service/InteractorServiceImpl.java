package uk.ac.ebi.reactome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.reactome.domain.model.Interactor;
import uk.ac.ebi.reactome.repository.InteractorRepository;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
@Service
public class InteractorServiceImpl implements InteractorService {

//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
private  final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    @Autowired
    private InteractorRepository interactorRepository;

    @Override
    public Interactor merge(Interactor interactor) {
        return interactorRepository.merge(interactor.getIntactId(), interactor.getName());
    }

    @Override
    public void createInteraction(String idA, String idB, Double score){
        if (!interactorRepository.createInteraction(idA, idB, score)) {
            logger.error("Adding Interaction between entry with dbId: " + idA + " and dbId: " + idB + " failed");
        }
    }
}
