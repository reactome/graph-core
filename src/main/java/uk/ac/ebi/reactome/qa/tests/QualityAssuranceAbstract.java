package uk.ac.ebi.reactome.qa.tests;

import uk.ac.ebi.reactome.qa.QualityAssurance;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 04.03.16.
 */
public abstract class QualityAssuranceAbstract implements QualityAssurance {

    @Override
    public void run() {

    }

    abstract String getName();



}
