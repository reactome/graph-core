package org.reactome.server.graph.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.reactome.server.graph.domain.result.ComponentOf;
import org.reactome.server.graph.domain.result.Referrals;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class AdvancedLinkageServiceTest extends BaseTest {

    @Autowired
    private AdvancedLinkageService advancedLinkageService;

    @BeforeClass
    public static void setUpClass() {
        logger.info(" --- !!! Running " + AdvancedLinkageServiceTest.class.getName() + "!!! --- \n");
    }

    @Test
    public void getComponentsOfTest() {

        logger.info("Started testing genericService.getComponentsOfTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<ComponentOf> componentOfs = advancedLinkageService.getComponentsOf("R-HSA-199420");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(componentOfs.size() > 0);
        logger.info("Finished");
    }

    @Test
    public void getReferralsToTest(){
        logger.info("Started testing genericService.getReferralsToTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Referrals> referrals = advancedLinkageService.getReferralsTo("R-HSA-71291");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There has to be 2 or more referrals", referrals.size() >= 1);
        logger.info("Finished");

    }
}