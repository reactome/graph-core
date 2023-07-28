package org.reactome.server.graph.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.Trackable;
import org.reactome.server.graph.domain.model.UpdateTracker;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateTrackerServiceTest extends BaseTest {

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        if (!checkedOnce) {
            isFit = fitForService();
            checkedOnce = true;
        }

        //*******   ENABLING LAZY LOADING FOR A PROPER TESTING  *********
        lazyFetchAspect.setEnableAOP(true);

        assertTrue(isFit);
        DatabaseObjectFactory.clearCache();
    }

    @Autowired
    UpdateTrackerService service;

    @Test
    void testFindUpdateTrackerByDbId() {

        Optional<UpdateTracker> updateTracker = this.service.findUpdateTrackerByDbId(9776257L);
        Assertions.assertNotNull(updateTracker);

        Assertions.assertEquals(77, updateTracker.get().getRelease().getReleaseNumber());

        List<Trackable> updatedInstances = updateTracker.get().getUpdatedInstance();

        Assertions.assertTrue(!updatedInstances.isEmpty());
        Assertions.assertTrue(updatedInstances.stream().anyMatch(trackable -> trackable instanceof Pathway));
    }


    @Test
    void testFindByUpdatedInstanceDbId() {
        List<UpdateTracker> updateTracker = this.service.findByUpdatedInstanceDbId(3299685L);
        assertTrue(updateTracker.size() >= 1);
    }

    @Test
    void testFindByUpdatedInstanceStId() {
        List<UpdateTracker> updateTracker = this.service.findByUpdatedInstanceStId("R-HSA-3299685");
        assertTrue(updateTracker.size() >= 1);
    }
}
