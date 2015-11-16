package uk.ac.ebi.reactome.controller;


import uk.ac.ebi.reactome.service.Service;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 13.11.15.
 */

public abstract class Controller<T> {
    public abstract Service<T> getService();
}
