package uk.ac.ebi.reactome.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.reactome.MyConfiguration;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.domain.model.ReferenceEntity;
import uk.ac.ebi.reactome.domain.result.LabelsCount;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 *
 *
 */

@ContextConfiguration(classes = { MyConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DatabaseObjectServiceTest {

//    @Autowired
//    private DatabaseObjectRepository databaseObjectRepository;

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

//        Iterable<DatabaseObject> x = databaseObjectService.findAll();
        DatabaseObject ob = databaseObjectService.findByDbId1(5205647L);


//        DatabaseObject databaseObject1 = DatabaseObjectFactory.createObject("5619102");
        DatabaseObject databaseObject2 = genericService.loadByProperty(DatabaseObject.class, "dbId", 5205647L);
//        TODO add actual tests

        DatabaseObject databaseObject3 = databaseObjectService.findByDbId2(5205647L);
        System.out.println();
    }



    @Test
    public void test() {
        Collection<LabelsCount> l = databaseObjectService.getLabelsCount();
        Map<String,Integer> map = new HashMap<>();
        for (LabelsCount labelsCount : l) {
            for (String s : labelsCount.getLabels()) {
                if(map.containsKey(s)) {
                    int i = map.get(s);
                    i += labelsCount.getCount();

                }
            }

        }
        System.out.println("");
    }

}