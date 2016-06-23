package org.reactome.server.graph.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactome.server.graph.config.Neo4jConfig;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.logging.Logger;

import static org.junit.Assume.assumeTrue;

/**
 * @author Guilherme Viteri (gviteri@ebi.ac.uk)
 * @since 14.06.16.
 */
@ContextConfiguration(classes = {Neo4jConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class AspectsTest {

    public static Logger logger = Logger.getLogger("testLogger");

    private static Boolean checkedOnce = false;
    private static Boolean isFit = false;

    @Autowired
    private GeneralService generalService;

    @Autowired
    private DatabaseObjectService dbs;

    @Before
    public void setUp() throws Exception {
        if (!checkedOnce) {
            isFit = generalService.fitForService();
            checkedOnce = true;
        }
        assumeTrue(isFit);
        generalService.clearCache();
        DatabaseObjectFactory.clearCache();
    }

    @Test
    public void lazyLoadingTest() {
        DatabaseObject dd = dbs.findByIdNoRelations("R-HSA-446203");
        Pathway pe = (Pathway)dd;
        System.out.println(pe.getHasEvent());
        System.out.println(pe.getOrthologousEvent());
        System.out.println(pe.getNormalPathway());
        System.out.println(pe.getCreated().getAuthor());
        System.out.println(pe.getModified().getNote());
    }

}