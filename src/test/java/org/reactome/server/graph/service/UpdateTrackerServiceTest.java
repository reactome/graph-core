package org.reactome.server.graph.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.TopLevelPathway;
import org.reactome.server.graph.domain.model.Trackable;
import org.reactome.server.graph.domain.model.UpdateTracker;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        Optional<UpdateTracker> updateTracker = this.service.findUpdateTrackerByDbId(testUpdateTracker.getDbId());
        Assertions.assertNotNull(updateTracker);

        Assertions.assertEquals(1, updateTracker.get().getRelease().getReleaseNumber());

        List<Trackable> updatedInstances = updateTracker.get().getUpdatedInstance();

        Assertions.assertTrue(!updatedInstances.isEmpty());
        Assertions.assertTrue(updatedInstances.stream().anyMatch(trackable -> trackable instanceof TopLevelPathway));
    }


    @Test
    void testFindByUpdatedInstanceDbId() {
        List<UpdateTracker> updateTracker = this.service.findByUpdatedInstanceDbId(Events.topLevelPathway.getDbId());
        assertEquals(testUpdateTracker.getStId(),updateTracker.get(0).getStId());
    }

    @Test
    void testFindByUpdatedInstanceStId() {
        List<UpdateTracker> updateTracker = this.service.findByUpdatedInstanceStId(Events.topLevelPathway.getStId());
        assertEquals(testUpdateTracker.getStId(),updateTracker.get(0).getStId());
    }
}
