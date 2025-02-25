package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Species;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.reactome.server.graph.service.BaseTest.homoSapiensSpecies;

@SpringBootTest
public class SpeciesServiceTest extends BaseTest {

    private final SpeciesService speciesService;

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
        Species species = speciesService.getSpecies(homoSapiensSpecies.getDbId());
        assertNotNull(species);
        assertEquals("HSA", species.getAbbreviation());
    }

    @Test
    public void testGetSpeciesByDbId(){
        Species species = speciesService.getSpeciesByDbId(homoSapiensSpecies.getDbId());
        assertNotNull(species);
        assertEquals("Homo sapiens", species.getDisplayName());
    }

    @Test
    public void testGetSpeciesByName(){
        testService.deleteTest();
        createSpecies(testService,"Test species");
        Species species = speciesService.getSpeciesByName("Test species");
        assertNotNull(species);
        assertEquals("123", species.getTaxId());
    }

    @Test
    public void testGetSpeciesByTaxId(){
        testService.deleteTest();
        createSpecies(testService,"Test species");
        Species species = speciesService.getSpeciesByTaxId("123");
        assertNotNull(species);
        assertEquals("Test species", species.getDisplayName());
    }
}
