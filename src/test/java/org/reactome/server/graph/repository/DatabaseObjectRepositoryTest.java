package org.reactome.server.graph.repository;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DatabaseObjectRepositoryTest {
    @Autowired
    private DatabaseObjectRepository databaseObjectRepository;

    @Test
    public void testDbId() {
        DatabaseObject db1 = databaseObjectRepository.findByDbId(69620L);
        System.out.println(db1);
        
        // No relationships
        DatabaseObject databaseObject = databaseObjectRepository.findByStId("R-HSA-69620");
        System.out.println(databaseObject);
        
        // Lazy Loading from SDN 4 suggested by Luanne
        //Pathway p = (Pathway) db;
        //p.getNormalPathway();
        //p.getSummation();
        
    }
}
