package org.reactome.server.graph.qa.tests;

import org.neo4j.ogm.model.Result;
import org.reactome.server.graph.qa.QATest;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 07.03.16.
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest014 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "InstanceEditWithoutAuthor";
    }

    @Override
    String getQuery() {
        return "Match (n:InstanceEdit) Where NOT (n)<-[:author]-() RETURN n.dbId AS dbId";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result,path,"dbId");
    }
}