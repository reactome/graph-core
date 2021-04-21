//package org.reactome.server.graph.repository;
//
//import org.junit.jupiter.api.Test;
//import org.reactome.server.graph.domain.model.Book;
//import org.reactome.server.graph.domain.model.DatabaseObject;
//import org.reactome.server.graph.domain.model.Pathway;
//import org.reactome.server.graph.service.helper.RelationshipDirection;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Example;
//
//import java.nio.file.Path;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//@SpringBootTest
//public class DatabaseObjectRepositoryTest {
//    @Autowired
//    private DatabaseObjectRepository databaseObjectRepository;
//    @Autowired
//    private DatabaseObjectRepo dbRepo;
//
//    @Autowired
//    private PathwayRepository pathwayRepository;
//
//    @Autowired
//    private BookRepository bk;
//
//    @Autowired
//    private AdvancedDatabaseObjectRepository adatabaseObjectRepository;
//
//    @Test
//    public void testDbId() throws ClassNotFoundException {
//        Pathway db1 = dbRepo.findById(164843L);
//        System.out.println(db1);
//
//        Pathway enhancedById = dbRepo.findEnhancedById(69620L);
//        System.out.println(enhancedById);
//
//
////        Book aa = bk.findByDbId(1810402L);
////        System.out.println(aa);
////        DatabaseObject db1 = databaseObjectRepository.findDatabaseObjectByDbId(69620L);
////        DatabaseObject db1 = databaseObjectRepository.run(69620L, Pathway.class);
//
////        DatabaseObject db1 = databaseObjectRepository.enhanced("R-HSA-69620");
//
////        DatabaseObject db1 = databaseObjectRepository.run(901056L);
////        DatabaseObject db1 = databaseObjectRepository.run(69620L);
////        Collection<Pathway> aa = databaseObjectRepository.getPathwaysForByStId("R-HSA-69620");
//
////        DatabaseObject db1 = databaseObjectRepository.findById2(69620L);
////        DatabaseObject db1 = databaseObjectRepository.run(69620L);
////
////        DatabaseObject db1 = databaseObjectRepository.run(69620L);
////
////        Pathway p = pathwayRepository.findByDbId(69620L);
////        System.out.println(p);
////        Pathway db2 = databaseObjectRepository.findByDbId(69620L);
////        System.out.println(db1);
////        adatabaseObjectRepository.queryRelationshipTypesByDbId(69620L, Pathway.class.getSimpleName(), RelationshipDirection.OUTGOING, "hasEvent");
//        // No relationships
////        DatabaseObject databaseObject = databaseObjectRepository.findByStId("R-HSA-69620");
////        System.out.println(databaseObject);
//
//        // Lazy Loading from SDN 4 suggested by Luanne
//        //Pathway p = (Pathway) db;
//        //p.getNormalPathway();
//        //p.getSummation();
//
//    }
//}
