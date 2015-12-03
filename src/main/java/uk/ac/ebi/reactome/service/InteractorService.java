package uk.ac.ebi.reactome.service;

import uk.ac.ebi.reactome.domain.model.Interactor;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
public interface InteractorService {

    Interactor merge(Interactor interactor);
    Interactor create(Interactor interactor);
    void createInteraction(String idA, String idB, Double score);

}
