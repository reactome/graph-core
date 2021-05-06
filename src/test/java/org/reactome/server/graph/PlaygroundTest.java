package org.reactome.server.graph;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.aop.LazyFetchAspect;
import org.reactome.server.graph.custom.CustomQueryComplex;
import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.domain.result.QueryResultWrapper;
import org.reactome.server.graph.exception.CustomQueryException;
import org.reactome.server.graph.repository.AdvancedDatabaseObjectRepository;
import org.reactome.server.graph.repository.CustomDTOsRepository;
import org.reactome.server.graph.repository.DatabaseObjectRepository;
import org.reactome.server.graph.repository.EventRepository;
import org.reactome.server.graph.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PlaygroundTest {

    @Autowired private AdvancedDatabaseObjectRepository advancedDatabaseObjectRepository;
    @Autowired private DatabaseObjectRepository databaseObjectRepository;
    @Autowired private EventRepository eventRepository;
    @Autowired private PersonService personService;
    @Autowired private CustomDTOsRepository customDTOsRepository;

    @Autowired
    private LazyFetchAspect lazyFetchAspect;

//    @BeforeEach
//    public void disableLazyFetch(){
//        lazyFetchAspect.setEnableAOP(false);
//    }

    @Test
    public void testInferredFromNull(){
        ReactionLikeEvent sas = databaseObjectRepository.findByDbId(202245L);
        assertNull(sas.getInferredFrom());
        assertEquals(sas.getInferredFrom().size(),0);
        System.out.println(sas);
    }

    @Test
    public void testHasCandidateSet(){
//        GenomeEncodedEntity sas = databaseObjectRepository.findByStId("R-HSA-5649637");
        CandidateSet sas = databaseObjectRepository.findByStId("R-HSA-5672709");
        System.out.println(sas);
    }

    //@Test
    public void testMultipleReactions () {
        List<String> reactionsStId = Arrays.asList("R-HSA-9626034", "R-RNO-9626032", "R-RNO-9626037","R-HSA-9626046",
                "R-HSA-9626067","R-RNO-9626036", "R-HSA-9626038","R-HSA-9626256", "R-RNO-9626254","R-RNO-9626239",
                "R-RNO-9626250",                "R-RNO-9626030",                "R-RNO-9626028",                "R-RNO-9625160",
                "R-RNO-9625173",                "R-RNO-9625183",                "R-RNO-9624154",                "R-RNO-9622851",
                "R-RNO-9622832",                "R-RNO-9620205",                "R-RNO-9626275",                "R-RNO-9626236",
                "R-RNO-9615712",                "R-HSA-9626253",                "R-HSA-9626242",                "R-HSA-9626039",
                "R-HSA-9626060",                "R-HSA-9625188",                "R-HSA-9625196",                "R-HSA-9625197",
                "R-HSA-9624158",                "R-HSA-9622831",                "R-HSA-9626276",                "R-HSA-9626235",
                "R-HSA-9622840",                "R-HSA-9620197",                "R-HSA-9615721",                "R-HSA-9613666",
                "R-MMU-9613670",                "R-MMU-9613562",                "R-MMU-9613545",                "R-MMU-9613507",
                "R-HSA-9613565",                "R-HSA-9613530",                "R-HSA-9613513",                "R-HSA-9613352",
                "R-RNO-9613356",                "R-HSA-9646685",                "R-MMU-9646685");
        for (String stId : reactionsStId) {
            ReactionLikeEvent sas = databaseObjectRepository.findByDbId(Long.parseLong(stId.replaceAll("R-[A-Z]{3}-","")));
            assertNotNull(sas);
            assertNotNull(sas.getStId());
        }
    }

    @Test
    public void testCustomQuery() throws CustomQueryException {
            String query = "MATCH (pe:Complex{speciesName:$species,stId:$stId})-[:hasComponent|hasMember|hasCandidate|repeatedUnit|referenceEntity*]->(re) " +
                    "RETURN pe.stId AS stId, pe.displayName AS displayName, COLLECT(re.dbId) as dbIds, COLLECT(re.databaseName) as databaseNames, " +
                    "COLLECT({database:re.databaseName, identifier:re.identifier}) AS customReferences";

            Map<String, Object> parametersMap = new HashMap<>();
            parametersMap.put("species", "Homo sapiens");
            parametersMap.put("stId", "R-HSA-1852614");

        CustomQueryComplex customComplexes = advancedDatabaseObjectRepository.customQueryResult(CustomQueryComplex.class, query, parametersMap);
            // In this test case, the relationships are mapped in the object CustomQueryComplex inside the Collection
//            Collection<CustomQueryComplex> customComplexes = advancedDatabaseObjectService.getCustomQueryResults(CustomQueryComplex.class, query, parametersMap);
    }

    @Test
    public void testQueryRelationshipTypesByDbId() {
//        Collection<PhysicalEntity> ss = advancedDatabaseObjectRepository.queryRelationshipTypesByDbId(189102L, "PhysicalEntity", RelationshipDirection.INCOMING, "output");
//        Collection<PhysicalEntity> aa = advancedDatabaseObjectRepository.queryRelationshipTypesByDbId(189102L, "PhysicalEntity", RelationshipDirection.OUTGOING, "output");
//         TODO check Output relationship
//        System.out.println(ss); // breakpoint here
//        System.out.println(aa); // breakpoint here
    }

    @Test
    public void testReactionLikEventOutput() {
        ReactionLikeEvent rle1 = databaseObjectRepository.findByDbId(8932955L);
        System.out.println(rle1);
        assertNotNull(rle1.getOutput());
        // TODO Fix here
//        assertEquals("R-ALL-83910", rle1.getOutput().iterator().next().getPhysicalEntity().getStId());
        ReactionLikeEvent rle2 = databaseObjectRepository.findByDbId(1247999L);
        System.out.println(rle2);
    }

    @Test
    public void testComplexIncludedLocation() {
        Complex complex = databaseObjectRepository.findByStId("R-HSA-9626061");
        System.out.println(complex);
    }

    @Test
    public void testEwas(){
        EntityWithAccessionedSequence ewas = databaseObjectRepository.findByStId("R-HSA-70298");
        System.out.println(ewas);
    }

    @Test
    public void testHasEncapsulatedEvent(){
        Pathway ewas = databaseObjectRepository.findByStId("R-HSA-69620");
        System.out.println(ewas);
    }

    @Test
    public void testHasMemberAndMemberOf(){
        DefinedSet set = databaseObjectRepository.findByStId("R-HSA-6803216");
        System.out.println(set);

        Complex complex = databaseObjectRepository.findByStId("R-HSA-6802615");
        System.out.println(complex);

        Complex complex2 = databaseObjectRepository.findByStId("R-HSA-8933620");
        System.out.println(complex2);

    }

    @Test public void testInput(){
        ReactionLikeEvent rle = databaseObjectRepository.findByStId("R-HSA-9626046");
        System.out.println(rle);

        EntityWithAccessionedSequence input1 = databaseObjectRepository.findByStId("R-HSA-9626022");
        System.out.println(input1);
        EntityWithAccessionedSequence input2 = databaseObjectRepository.findByStId("R-HSA-9626054");
        System.out.println(input2);

        Complex output = databaseObjectRepository.findByStId("R-HSA-9626070");
        System.out.println(output);
    }

    @Test public void testPublicationAuthor() {
        LiteratureReference rle = databaseObjectRepository.findByDbId(192633L);
        System.out.println(rle);
        Person person = databaseObjectRepository.findByDbId(169005L);
        person.getAffiliation();
    }

    @Test public void testRepeatedUnitAndHasModifiedResidue() {
        Polymer rle = databaseObjectRepository.findByStId("R-HSA-5340146");
        System.out.println(rle);
        EntityWithAccessionedSequence input2 = databaseObjectRepository.findByStId("R-DDI-4793916");
        input2.getRepeatedUnitOf();
    }

    @Test public void testInferredTo() {
        EntityWithAccessionedSequence rle = databaseObjectRepository.findByStId("R-SSC-5684062");
        System.out.println(rle);
    }

    @Test public void testLazyLoading() {
      lazyFetchAspect.setEnableAOP(false);
        EntityWithAccessionedSequence rle = databaseObjectRepository.findByStIdNoRelations("R-SSC-5684062");
        rle.getCompartment();
        System.out.println(rle);
    }

    @Test public void testLazyLoadingOutput() {
        lazyFetchAspect.setEnableAOP(true);
        System.out.println(lazyFetchAspect.hashCode());
        ReactionLikeEvent rle = databaseObjectRepository.findByStIdNoRelations("R-HSA-3234081");
        rle.getOutput();
        System.out.println(rle);
    }

    @Test public void testQueryResultWrapper() {
        Collection<QueryResultWrapper> wrappers = advancedDatabaseObjectRepository.queryResultWrappers(3234081L);
        assertThat(wrappers).containsExactlyInAnyOrder(
                new QueryResultWrapper(new Complex(), 1),
                new QueryResultWrapper(new EntityWithAccessionedSequence(), 2));
    }


}
