package uk.ac.ebi.reactome.qualityassurance;

import uk.ac.ebi.reactome.service.GenericService;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 04.03.16.
 */
public interface QualityAssurance {

    void run(GenericService genericService);
}
