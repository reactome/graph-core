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
import uk.ac.ebi.reactome.domain.model.EntityWithAccessionedSequence;
import uk.ac.ebi.reactome.repository.DatabaseObjectRepository;
import uk.ac.ebi.reactome.repository.GenericRepository;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class DatabaseObjectServiceTest {

    @Autowired
    private DatabaseObjectService databaseObjectService;

    @Autowired
    private DatabaseObjectRepository databaseObjectRepository;

    @Autowired
    private GenericRepository genericRepository;

    @Autowired
    private GenericService genericService;

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testParticipating() {
        long start1 = System.currentTimeMillis();
        Collection<EntityWithAccessionedSequence> coll = databaseObjectRepository.getParticipating(75153L);
        long time1 = System.currentTimeMillis()-start1;
        System.out.println();
    }

    @Test
    public void findByDbId() {

        DatabaseObject x = databaseObjectService.findByDbId(870392L);
//        DatabaseObject t = databaseObjectService.findByDbId(870392L);
//        DatabaseObject z = databaseObjectService.findByDbId(870392L);
//        DatabaseObject u = databaseObjectService.findByDbId(870392L);

//
//        long start6 = System.currentTimeMillis();
//        DatabaseObject databaseO = genericService.findByDbId(DatabaseObject.class, 1214188L, 1);
//        long time6 = System.currentTimeMillis()-start6;


//        long start1 = System.currentTimeMillis();
//        DatabaseObject databaseObject1 = DatabaseObjectFactory.createObject("1214188");
//        databaseObject1.load();
//        long time1 = System.currentTimeMillis()-start1;


//        long startS1 = System.currentTimeMillis();
//        DatabaseObject databaseObjectS1 = DatabaseObjectFactory.createObject("R-HSA-1214188");
//        databaseObjectS1.load();
//        long timeS1 = System.currentTimeMillis()-startS1;
//

//        long startS2 = System.currentTimeMillis();
//        DatabaseObject databaseObject2 = databaseObjectService.findByStableIdentifier("R-HSA-1214188");
//        long timeS2 = System.currentTimeMillis()-startS2;
//


                long start1 = System.currentTimeMillis();
        DatabaseObject databaseObject1 = DatabaseObjectFactory.createObject("5619102");
        databaseObject1.load();
        //will have less loaded
        long time1 = System.currentTimeMillis()-start1;


        long start2 = System.currentTimeMillis();
        DatabaseObject databaseObject2 = genericRepository.loadByProperty(DatabaseObject.class,"dbId", 5619102L);
        long time2 = System.currentTimeMillis()-start2;
//
        long start8 = System.currentTimeMillis();
        databaseObject2 = genericService.findByDbId(DatabaseObject.class, 5619102L, 1);
        long time8 = System.currentTimeMillis()-start8;


//        long start2 = System.currentTimeMillis();
//        DatabaseObject databaseObject2 = databaseObjectService.findByDbId3(5619102L);
//        long time2 = System.currentTimeMillis()-start2;

//        Long id = databaseObject2.getId();
//        databaseObject2 = null;
//
//
//        long start3 = System.currentTimeMillis();
//        databaseObject2 = databaseObjectService.findByDbId2(1214188L);
//        long time3 = System.currentTimeMillis()-start3;
//
//        databaseObject2 = null;
//
//        long start4 = System.currentTimeMillis();
//        databaseObject2 = databaseObjectService.findByDbId(1214188L);
//        long time4 = System.currentTimeMillis()-start4;
//
//        databaseObject2 = null;
//
//        long start5 = System.currentTimeMillis();
//        databaseObject2 = databaseObjectService.findOne(id,1);
//        long time5 = System.currentTimeMillis()-start5;





//        long start3 = System.currentTimeMillis();
//        DatabaseObject databaseObject3 = databaseObjectService.findByDbId2(1214188L);
//        long time3 = System.currentTimeMillis()-start3;

        System.out.println();
    }


}