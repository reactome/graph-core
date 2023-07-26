package org.reactome.server.graph.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Complex;
import org.reactome.server.graph.domain.model.Reaction;
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
    void testFindById() {
        Optional<UpdateTracker> updateTracker = this.service.findById(999999999999999L);
        assertTrue(updateTracker.isPresent());
        List<Trackable> updatedInstances = updateTracker.get().getUpdatedInstance();
        assertEquals(2, updatedInstances.size());
        assertEquals(77, updateTracker.get().getRelease().getReleaseNumber());
        assertTrue(updatedInstances.stream().anyMatch(trackable -> trackable instanceof Reaction));
        assertTrue(updatedInstances.stream().anyMatch(trackable -> trackable instanceof Complex));
    }

    @Test
    void testFindByReleaseNumber() {
        List<UpdateTracker> updateTracker = this.service.findByReleaseNumber(77);
        assertTrue(updateTracker.size() >= 1);
    }

    @Test
    void testFindByUpdatedInstanceDbId() {
        List<UpdateTracker> updateTracker = this.service.findByUpdatedInstanceDbId(6793661L);
        assertTrue(updateTracker.size() >= 1);
    }

    @Test
    void testFindByUpdatedInstanceStId() {
        List<UpdateTracker> updateTracker = this.service.findByUpdatedInstanceStId("R-HSA-8944365");
        assertTrue(updateTracker.size() >= 1);
    }

}
