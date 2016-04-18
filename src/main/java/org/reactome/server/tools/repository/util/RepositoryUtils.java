package org.reactome.server.tools.repository.util;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */

public class RepositoryUtils {

    public static String getRelationshipAsString (String... relationships) {
        String result = "";
        if (relationships != null && relationships.length > 0) {
            String pipe = ":";
            for (String relationship : relationships) {
                result += pipe + relationship;
                pipe = "|";
            }
        }
        return result;
    }
}
