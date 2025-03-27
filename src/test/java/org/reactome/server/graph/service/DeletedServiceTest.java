package org.reactome.server.graph.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class DeletedServiceTest extends BaseTest {

    @Autowired
    DeletedService service;

    @Test
    public void testFindDeletedByDbId() {

        Optional<Deleted> deletedObject = service.getDeletedByDbId(deleted.getDbId());
        Assertions.assertTrue(deletedObject.isPresent());

        List<DeletedInstance> deletedInstances = deletedObject.get().getDeletedInstance();
        Assertions.assertTrue(!deletedInstances.isEmpty());

        List<Deletable> deletables = deletedObject.get().getReplacementInstances();
        Assertions.assertTrue(!deletables.isEmpty());
    }

    @Test
    public void testGetByDeletedInstanceDbId() {
        List<Deleted> deletes = this.service.getByDeletedInstanceDbId(deletedInstance.getDbId());
        Assertions.assertFalse(deletes.isEmpty());
    }

    @Test
    public void testGetDeletedByReplacementInstancesStId() {
        List<Deleted> deletes = this.service.getByReplacementStId(PhysicalEntities.negativeRegulation.getStId());
        Assertions.assertFalse(deletes.isEmpty());
    }
}
