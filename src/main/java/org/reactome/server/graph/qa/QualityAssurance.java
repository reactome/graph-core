package org.reactome.server.graph.qa;

import org.reactome.server.graph.service.GeneralService;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 04.03.16.
 */
public interface QualityAssurance {

    void run(GeneralService genericService);
}
