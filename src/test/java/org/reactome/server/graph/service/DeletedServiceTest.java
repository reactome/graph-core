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

        Optional<Deleted> deleted = service.getDeletedByDbId(8869329L);
        Assertions.assertTrue(deleted.isPresent());

        List<DeletedInstance> deletedInstances = deleted.get().getDeletedInstance();
        Assertions.assertTrue(!deletedInstances.isEmpty());

        List<Deletable> deletables = deleted.get().getReplacementInstances();
        Assertions.assertTrue(!deletables.isEmpty());

        DeletedControlledVocabulary dcv = deleted.get().getReason();
        Assertions.assertNotNull(dcv);

        Assertions.assertTrue(deletables.stream().anyMatch(deletable -> deletable instanceof NegativeRegulation));
    }

    @Test
    public void testGetByDeletedInstanceDbId() {
        List<Deleted> deleteds = this.service.getByDeletedInstanceDbId(9745855L);
        Assertions.assertTrue(!deleteds.isEmpty());
    }

    @Test
    public void testGetDeletedByReplacementInstancesStId() {
        List<Deleted> deleteds = this.service.getByReplacementStId("R-RNO-164402");
        Assertions.assertTrue(!deleteds.isEmpty());
    }
}
