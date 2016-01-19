package uk.ac.ebi.reactome.data;

import org.gk.model.GKInstance;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 18.01.16.
 */
public class GkInstanceCardinalityHelper {

    private GKInstance instance;
    private Integer count;

    public GkInstanceCardinalityHelper(GKInstance instance, Integer count) {
        this.instance = instance;
        this.count = count;
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
