package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Species;
import org.reactome.server.graph.util.TestNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.reactome.server.graph.service.BaseTest.homoSapiensSpecies;

@SpringBootTest
public class SpeciesServiceTest extends BaseTest {

    private final SpeciesService speciesService;
    @Autowired
    private TestNodeService testNodeService;

    @Autowired
    public SpeciesServiceTest(SpeciesService speciesService) {
        this.speciesService = speciesService;
    }

    @Test
    public void testGetAllSpecies(){
        List<Species> allSpecies = speciesService.getAllSpecies();
        assertNotNull(allSpecies);
        assertTrue(allSpecies.size() >= 15);
    }

    @Test
    public void testGetSpecies(){
        List<Species> allSpeciesHumanFirst = speciesService.getSpecies();
        assertNotNull(allSpeciesHumanFirst);
        assertEquals("HSA", allSpeciesHumanFirst.get(0).getAbbreviation());
    }

    @Test
    public void testGetSpeciesAnyId(){
        Species species = speciesService.getSpecies(testSpecies.getDbId());
        assertNotNull(species);
        assertEquals("HSA", species.getAbbreviation());
    }

    @Test
    public void testGetSpeciesByDbId(){
        Species species = speciesService.getSpeciesByDbId(testSpecies.getDbId());
        assertNotNull(species);
        assertEquals(testSpecies.getDisplayName(), species.getDisplayName());
    }

    @Test
    public void testGetSpeciesByName(){
        Species species = speciesService.getSpeciesByName(testSpecies.getDisplayName());
        assertNotNull(species);
        assertEquals(testSpecies.getTaxId(), species.getTaxId());
    }

    @Test
    public void testGetSpeciesByTaxId(){
        Species species = speciesService.getSpeciesByTaxId(testSpecies.getTaxId());
        assertNotNull(species);
        assertEquals(testSpecies.getDisplayName(), species.getDisplayName());
    }
}
