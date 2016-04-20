package org.reactome.server.graph.qa.tests;

import org.neo4j.ogm.model.Result;
import org.reactome.server.graph.qa.QATest;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 08.03.16.
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest024 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "ReferenceDatabaseWithoutUrls";
    }

    @Override
    String getQuery() {
        return "Match (n:ReferenceDatabase)<-[:created]-(a) Where n.accessUrl is NULL OR n.url is NULL RETURN n.dbId AS dbId, " +
                "n.displayName AS name, a.displayName as author";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result,path,"dbId","name","author");
    }
}




