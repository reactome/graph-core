package org.reactome.server.tools.qa;

import org.reactome.server.tools.service.GeneralService;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 04.03.16.
 */
public interface QualityAssurance {

    void run(GeneralService genericService);
}
