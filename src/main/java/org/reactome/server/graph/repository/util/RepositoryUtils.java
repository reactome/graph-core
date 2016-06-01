package org.reactome.server.graph.repository.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 */
public class RepositoryUtils {

    public static String getRelationshipAsString (String... relationships) {
        String result = "";
        if (relationships != null && relationships.length > 0) {
            result = ":" + StringUtils.join(relationships, "|");
        }
        return result;
    }
}
