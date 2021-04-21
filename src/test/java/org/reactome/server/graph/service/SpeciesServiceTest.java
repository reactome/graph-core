package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Species;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SpeciesServiceTest {

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
        Species species = speciesService.getSpecies(9606);
        assertNotNull(species);
        assertEquals("HSA", species.getAbbreviation());

        species = speciesService.getSpecies("Bos taurus");
        assertNotNull(species);
        assertEquals("BTA", species.getAbbreviation());
    }

    @Test
    public void testGetSpeciesByDbId(){
        Species species = speciesService.getSpeciesByDbId(48892L);
        assertNotNull(species);
        assertEquals("Mus musculus", species.getDisplayName());
    }

    @Test
    public void testGetSpeciesByName(){
        Species species = speciesService.getSpeciesByName("Homo sapiens");
        assertNotNull(species);
        assertEquals("9606", species.getTaxId());
        assertEquals(48887L, species.getDbId());
    }

    @Test
    public void testGetSpeciesByTaxId(){
        Species species = speciesService.getSpeciesByTaxId("9606");
        assertNotNull(species);
        assertEquals(48887L, species.getDbId());
        assertEquals("Homo sapiens", species.getDisplayName());
    }
}
