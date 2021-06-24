package org.reactome.server.graph.repository.util;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 */
public class RepositoryUtils {

    public static String getRelationshipAsString (Collection<String> relationships) {
        String result = "";
        if (relationships != null && relationships.size() > 0) {
            result = ":" + String.join("|", relationships);
        }
        return result;
    }
}
