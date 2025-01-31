package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.result.ComponentOf;
import org.reactome.server.graph.domain.result.Referrals;
import org.reactome.server.graph.util.TestNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@EnableNeo4jRepositories(basePackages = "org.reactome.server.graph.util")
public class AdvancedLinkageServiceTest extends BaseTest {

    @Autowired
    private AdvancedLinkageService advancedLinkageService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running {}!!! --- \n", AdvancedLinkageServiceTest.class.getName());
        System.out.println("run");
    }

    @AfterTestClass
    public void tearDownClass() {
        System.out.println("tearDown");
        logger.info(" --- Finished:  {}  Delete Mock Database Entries --- \n", AdvancedLinkageServiceTest.class.getName());
    }

    @Test
    public void getComponentOfTest(){
        long start, time;

        logger.info("Started testing genericService.getComponentOfTest");
        start = System.currentTimeMillis();
        Collection<ComponentOf> componentOfs = advancedLinkageService.getComponentsOf(-1);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(componentOfs.size() == 1);
        logger.info("Finished");
    }


    @Test
    public void getReferralsToTest(){
        logger.info("Started testing genericService.getReferralsToTest");

        long start, time;
        start = System.currentTimeMillis();
        Collection<Referrals> referrals = advancedLinkageService.getReferralsTo("R-HSA-123456");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");


        assertTrue(referrals.size() == 1);
        logger.info("Finished");
    }
}