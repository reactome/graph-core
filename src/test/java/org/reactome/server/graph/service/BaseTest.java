package org.reactome.server.graph.service;

import org.junit.jupiter.api.BeforeEach;
import org.reactome.server.graph.aop.LazyFetchAspect;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
@SpringBootTest
public abstract class BaseTest {

    protected static final Logger logger = LoggerFactory.getLogger("testLogger");

    static Boolean checkedOnce = false;
    static Boolean isFit = false;

    @Autowired
    protected GeneralService generalService;

    @Autowired
    protected LazyFetchAspect lazyFetchAspect;

    @AfterTestClass
    public void tearDownClass() {
        logger.info("\n\n");
    }

    @BeforeEach
    public void setUp() throws Exception {
        if (!checkedOnce) {
            isFit = generalService.fitForService();
            checkedOnce = true;
        }

        /* DISABLING LAZY LOADING FOR A PROPER TESTING */
        lazyFetchAspect.setEnableAOP(false);

        assumeTrue(isFit);
        DatabaseObjectFactory.clearCache();
    }
}
