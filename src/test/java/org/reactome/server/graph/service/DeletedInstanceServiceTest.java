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
        Long dbId = deletedInstance.getDbId();
        Optional<DeletedInstance> deletedInstance = this.deletedInstanceService.getDeletedInstanceByDbId(dbId);
        Assertions.assertTrue(deletedInstance.isPresent());

        Assertions.assertNotNull(deletedInstance.get().getDeletedInstanceDbId());
        Assertions.assertNotNull(deletedInstance.get().getDeletedStId());
    }

    @Test
    public void getByDeletedDbId() {
        List<DeletedInstance> deletedInstances = this.deletedInstanceService.getByDeletedDbId(deleted.getDbId());
        Assertions.assertFalse(deletedInstances.isEmpty());
    }
}
