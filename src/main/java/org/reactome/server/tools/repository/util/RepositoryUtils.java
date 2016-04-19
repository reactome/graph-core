package org.reactome.server.tools.repository.util;

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
            String pipe = ":";
            for (String relationship : relationships) {
                result += pipe + relationship;
                pipe = "|";
            }
        }
        return result;
    }
}
