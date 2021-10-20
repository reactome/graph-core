package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.result.ComponentOf;
import org.reactome.server.graph.domain.result.Referrals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class AdvancedLinkageServiceTest extends BaseTest {

    @Autowired
    private AdvancedLinkageService advancedLinkageService;

    @BeforeTestClass
    public void setUpClass() {
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

        assertTrue(referrals.size() >= 1, "There has to be 2 or more referrals");
        logger.info("Finished");
    }
}