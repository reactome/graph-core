//package uk.ac.ebi.reactome.service.placeholder;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import uk.ac.ebi.reactome.Application;
//import uk.ac.ebi.reactome.service.GenericService;
//import uk.ac.ebi.reactome.service.ImportService;
//
///**
// * Created by:
// *
// * @author Florian Korninger (florian.korninger@ebi.ac.uk)
// * @since 23.11.15.
// *
// * Creating the constraints and indexes will not be tested. The view the existing indexes or constraints please use the
// * Neo4j Browser (localhost:7474/browser/ and query for ":schema" or use the REST API http://localhost:7474/db/data/schema
// * Authentication has to be provided in the Header.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//public class ImportServiceTest {
//
//    @Autowired
//    private ImportService importService;
//
//    @Autowired
//    private GenericService genericService;
//
//    @Before
//    private void setUp() {
////        importService.getOrCreate(new Pathway (1L, "React_1.1", "Pathway"));
////        importService.getOrCreate(new Reaction(2L, "React_2.1", "Reaction"));
////        importService.getOrCreate(new EntityWithAccessionedSequence(3L, "React_3.1", "EWAS"));
////        importService.getOrCreate(new Complex(4L, "React_4.1", "Complex"));
////        importService.getOrCreate(new OpenSet(5L, "React_5.1", "Set"));
//    }
//
//    @After
//    private void tearDown() {
////        genericService.cleanDatabase();
//    }
//
//    @Test
//    public void getOrCreate() {
////        Long count = genericService.countEntries(DatabaseObject.class);
////        importService.getOrCreate();
//
//
////        assertEquals(count+4, genericService.countEntries(DatabaseObject.class);
////        assertEquals();
//    }
//
//
////    DatabaseObject getOrCreate(DatabaseObject databaseObject);
////
////    /*
////    Methods for adding relationships to two entities by their dbIds.
////     */
////    void createInputRelationship(Long dbIdA, Long dbIdB);
////    void createOutputRelationship(Long dbIdA, Long dbIdB);
////    void createCatalystRelationship(Long dbIdA, Long dbIdB);
////    void createCandidateRelationship(Long dbIdA, Long dbIdB);
////    void createComponentRelationship(Long dbIdA, Long dbIdB);
////    void createMemberRelationship(Long dbIdA, Long dbIdB);
////    void createEventRelationship(Long dbIdA, Long dbIdB);
////    void createRepeatedUnitRelationship(Long dbIdA, Long dbIdB);
////    void createReferenceEntityRelationship(Long dbIdA, Long dbIdB);
//
//}
