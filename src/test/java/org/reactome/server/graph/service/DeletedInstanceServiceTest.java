package org.reactome.server.graph.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.DeletedInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class DeletedInstanceServiceTest extends BaseTest {

    @Autowired
    private DeletedInstanceService deletedInstanceService;


    @Test
    public void getDeletedInstanceByDbId() {
        Optional<DeletedInstance> deletedInstance = this.deletedInstanceService.getDeletedInstanceByDbId(9745160L);
        Assertions.assertTrue(deletedInstance.isPresent());

        int deletedInstanceDbId = deletedInstance.get().getDeletedInstanceDbId();
        Assertions.assertNotNull(deletedInstanceDbId);

        String deletedStId = deletedInstance.get().getDeletedStId();
        Assertions.assertNotNull(deletedStId);
    }

    @Test
    public void getByDeletedDbId() {
        List<DeletedInstance> deletedInstances = this.deletedInstanceService.getByDeletedDbId(5655667L);
        Assertions.assertFalse(deletedInstances.isEmpty());
    }
}
