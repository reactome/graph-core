package uk.ac.ebi.reactome.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.reactome.Application;
import uk.ac.ebi.reactome.data.DatabaseObjectFactory;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.domain.model.ReferenceEntity;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 *
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class DatabaseObjectServiceTest {

    @Autowired
    private DatabaseObjectService databaseObjectService;

    @Autowired
    private GenericService genericService;

    @Before
    public void setUp() {}

    @After
    public void tearDown() {}


    /**
     *
     * !!!!!!!!!   Execution Times will be monitored by LoggingAspect !!!!!!!!!!!!
     *
     */


    @Test
    public void testGetParticipatingMolecules() {
        Collection<ReferenceEntity> coll = databaseObjectService.getParticipatingMolecules(75153L);
//        TODO add actual tests

    }

    @Test
    public void testFindByDbId() {

        DatabaseObject databaseObject1 = DatabaseObjectFactory.createObject("5619102");
        DatabaseObject databaseObject2 = genericService.loadByProperty(DatabaseObject.class, "dbId", 5619102L);
//        TODO add actual tests


    }
}