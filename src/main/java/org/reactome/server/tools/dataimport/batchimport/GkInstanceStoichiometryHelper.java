package org.reactome.server.tools.dataimport.batchimport;

import org.gk.model.GKInstance;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 18.01.16.
 */
class GkInstanceStoichiometryHelper {

    private final GKInstance instance;
    private Integer count;

    public GkInstanceStoichiometryHelper(GKInstance instance) {
        this.instance = instance;
        this.count = 1;
    }

    public GKInstance getInstance() {
        return instance;
    }

    public Integer getCount() {
        return count;
    }

    public void increment() {
        count++;
    }
}
