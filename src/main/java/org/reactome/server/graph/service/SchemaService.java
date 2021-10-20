package org.reactome.server.graph.service;

import org.apache.commons.lang3.StringUtils;
import org.reactome.server.graph.domain.model.ExternalOntology;
import org.reactome.server.graph.domain.model.ReferenceEntity;
import org.reactome.server.graph.domain.model.Species;
import org.reactome.server.graph.domain.result.SimpleDatabaseObject;
import org.reactome.server.graph.domain.result.SimpleReferenceObject;
import org.reactome.server.graph.repository.SchemaRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 */
@Service
@SuppressWarnings("WeakerAccess")
public class SchemaService {

    @Autowired
    private SchemaRepository schemaRepository;

    // ---------------------------------------- Query by Class --------------------------------------------------

    public <T> Collection<T> getByClass(Class<T> clazz) {
        return schemaRepository.getByClass(clazz);
    }

    public <T> Collection<T> getByClass(Class<T> clazz, Object species) {
        if (isValidSpeciesClass(clazz)) {
            return getByClassAndSpecies(clazz,species);
        }
        return null;
    }

    public <T> Collection<T> getByClassName(String className) throws ClassNotFoundException {
        Class clazz = DatabaseObjectUtils.getClassForName(className);
        return schemaRepository.getByClass(clazz);
    }

    public <T> Collection<T> getByClassName(String className, Object species) throws ClassNotFoundException {
        Class clazz = DatabaseObjectUtils.getClassForName(className);
        if (isValidSpeciesClass(clazz)) {
            return getByClassAndSpecies(clazz,species);
        }
        return null;
    }

    // ------------------------------------ Query by Class (pageing) -----------------------------------------------

    public <T> Collection<T> getByClass(Class<T> clazz, Integer page, Integer offset) {
        return schemaRepository.getByClass(clazz, page, offset);
    }

    public <T> Collection<T> getByClass(Class<T> clazz, Object species, Integer page, Integer offset) {
        if (isValidSpeciesClass(clazz)) {
            return getByClassAndSpecies(clazz,species, page, offset);
        }
        return null;
    }

    public <T> Collection<T> getByClassName(String className, Integer page, Integer offset) throws ClassNotFoundException {
        Class clazz = DatabaseObjectUtils.getClassForName(className);
        return schemaRepository.getByClass(clazz, page, offset);
    }

    public <T> Collection<T> getByClassName(String className, Object species, Integer page, Integer offset) throws ClassNotFoundException {
        Class clazz = DatabaseObjectUtils.getClassForName(className);
        if (isValidSpeciesClass(clazz)) {
            return getByClassAndSpecies(clazz,species, page, offset);
        }
        return null;
    }

    public Integer countByClassAndSpecies(String className, Object species) throws ClassNotFoundException {
        Class clazz = DatabaseObjectUtils.getClassForName(className);
        if (isValidSpeciesClass(clazz)) {
            return countByClassAndSpecies(clazz,species);
        }
        return 0;
    }

    // ---------------------------------------- Query by Class for SimpleObject ------------------------------------------------

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClass(Class clazz) {
        return schemaRepository.getSimpleDatabaseObjectByClass(clazz);
    }

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClass(Class clazz, Object species) {
        if (isValidSpeciesClass(clazz)) {
            return getSimpleDatabaseObjectByClassAndSpecies(clazz,species);
        }
        return null;
    }

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClassName(String className) throws ClassNotFoundException {
        Class clazz = DatabaseObjectUtils.getClassForName(className);
        return schemaRepository.getSimpleDatabaseObjectByClass(clazz);
    }

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClassName(String className, Object species) throws ClassNotFoundException {
        Class clazz = DatabaseObjectUtils.getClassForName(className);
        if (isValidSpeciesClass(clazz)) {
            return getSimpleDatabaseObjectByClassAndSpecies(clazz,species);
        }
        return null;
    }

    // ---------------------------------------- Query by Class for SimpleObject (paging) ------------------------------------------------

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClass(Class clazz, Integer page, Integer offset) {
        return schemaRepository.getSimpleDatabaseObjectByClass(clazz, page, offset);
    }

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClass(Class clazz, Object species, Integer page, Integer offset) {
        if (isValidSpeciesClass(clazz)) {
            return getSimpleDatabaseObjectByClassAndSpecies(clazz,species, page, offset);
        }
        return null;
    }

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClassName(String className, Integer page, Integer offset) throws ClassNotFoundException {
        Class clazz = DatabaseObjectUtils.getClassForName(className);
        return schemaRepository.getSimpleDatabaseObjectByClass(clazz, page, offset);
    }

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClassName(String className, Object species, Integer page, Integer offset) throws ClassNotFoundException {
        Class clazz = DatabaseObjectUtils.getClassForName(className);
        if (isValidSpeciesClass(clazz)) {
            return getSimpleDatabaseObjectByClassAndSpecies(clazz,species, page, offset);
        }
        return null;
    }

    // ---------------------------------------- Query by Class for SimpleReferenceObject ------------------------------------------------

    public Collection<SimpleReferenceObject> getSimpleReferencesObjectsByClass(Class clazz) {
        if (isValidReferenceClass(clazz)) {
            return schemaRepository.getSimpleReferencesObjectsByClass(clazz);
        }
        return null;
    }

    public Collection<SimpleReferenceObject> getSimpleReferencesObjectsByClass(Class clazz, Integer page, Integer offset) {
        if (isValidReferenceClass(clazz)) {
            return schemaRepository.getSimpleReferencesObjectsByClass(clazz, page, offset);
        }
        return null;
    }

    public Collection<SimpleReferenceObject> getSimpleReferencesObjectsByClassName(String className) throws ClassNotFoundException {
        Class clazz = DatabaseObjectUtils.getClassForName(className);
        if (isValidReferenceClass(clazz)) {
            return schemaRepository.getSimpleReferencesObjectsByClass(clazz);
        }
        return null;
    }

    public Collection<SimpleReferenceObject> getSimpleReferencesObjectsByClassName(String className, Integer page, Integer offset) throws ClassNotFoundException {
        Class clazz = DatabaseObjectUtils.getClassForName(className);
        if (isValidReferenceClass(clazz)) {
            return schemaRepository.getSimpleReferencesObjectsByClass(clazz, page, offset);
        }
        return null;
    }

    // ---------------------------------------- Query by Class for single value ------------------------------------------------

    public Collection<String> getStIdsByClass (Class clazz) {
        return schemaRepository.getStIdsByClass(clazz);
    }

    public Collection<String> getStIdsByClass (String className) throws ClassNotFoundException {
        Class clazz = DatabaseObjectUtils.getClassForName(className);
        return schemaRepository.getStIdsByClass(clazz);
    }

    public Collection<Long> getDbIdsByClass (Class clazz) {
        return schemaRepository.getDbIdsByClass(clazz);
    }

    public Collection<Long> getDbIdsByClass (String className) throws ClassNotFoundException {
        Class clazz = DatabaseObjectUtils.getClassForName(className);
        return schemaRepository.getDbIdsByClass(clazz);
    }

    // ---------------------------------------- Count by Class ------------------------------------------------

    public Long countEntries(Class<?> clazz){
        return schemaRepository.countEntries(clazz);
    }

    public Long countEntries(Class<?> clazz, Object species){
        if (isValidSpeciesClass(clazz)) {
            return countEntriesWithSpecies(clazz, species);
        }
        return null;
    }

    public Long countEntries(String className) throws ClassNotFoundException {
        Class clazz = DatabaseObjectUtils.getClassForName(className);
        return schemaRepository.countEntries(clazz);
    }

    public Long countEntries(String className, Object species) throws ClassNotFoundException {
        Class clazz = DatabaseObjectUtils.getClassForName(className);
        if (isValidSpeciesClass(clazz)) {
            return countEntriesWithSpecies(clazz, species);
        }
        return null;
    }

    // ---------------------------------------- private methods ------------------------------------------------

    private Boolean isValidSpeciesClass(Class clazz) {
        try {
            //noinspection unused,unchecked
            Method m = clazz.getMethod("getSpecies");
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private Boolean isValidReferenceClass(Class clazz) {
        return ReferenceEntity.class.isAssignableFrom(clazz) || ExternalOntology.class.isAssignableFrom(clazz);
    }

    private <T> Collection<T> getByClassAndSpecies(Class clazz, Object species) {
        String speciesString = species instanceof Species ? ((Species) species).getTaxId() : species.toString();
        if (StringUtils.isNumeric(speciesString)) {
            return schemaRepository.getByClassAndSpeciesTaxId(clazz, speciesString);
        } else {
            return schemaRepository.getByClassAndSpeciesName(clazz, speciesString);
        }
    }

    private <T> Collection<T> getByClassAndSpecies(Class clazz, Object species, Integer page, Integer offset) {
        String speciesString = species instanceof Species ? ((Species) species).getTaxId() : species.toString();
        if (StringUtils.isNumeric(speciesString)) {
            return schemaRepository.getByClassAndSpeciesTaxId(clazz, speciesString, page, offset);
        } else {
            return schemaRepository.getByClassAndSpeciesName(clazz, speciesString, page, offset);
        }
    }


    private Integer countByClassAndSpecies(Class clazz, Object species) {
        String speciesString = species instanceof Species ? ((Species) species).getTaxId() : species.toString();
        if (StringUtils.isNumeric(speciesString)) {
            return schemaRepository.countByClassAndSpeciesTaxId(clazz, speciesString);
        } else {
            return schemaRepository.countByClassAndSpeciesName(clazz, speciesString);
        }
    }

    private Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClassAndSpecies(Class clazz, Object species) {
        String speciesString = species instanceof Species ? ((Species) species).getTaxId() : species.toString();
        if (StringUtils.isNumeric(speciesString)) {
            return schemaRepository.getSimpleDatabaseObjectByClassAndSpeciesTaxId(clazz,speciesString);
        } else {
            return schemaRepository.getSimpleDatabaseObjectByClassAndSpeciesName(clazz,speciesString);
        }
    }

    private Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClassAndSpecies(Class clazz, Object species, Integer page, Integer offset) {
        String speciesString = species instanceof Species ? ((Species) species).getTaxId() : species.toString();
        if (StringUtils.isNumeric(speciesString)) {
            return schemaRepository.getSimpleDatabaseObjectByClassAndSpeciesTaxId(clazz,speciesString, page, offset);
        } else {
            return schemaRepository.getSimpleDatabaseObjectByClassAndSpeciesName(clazz,speciesString, page, offset);
        }
    }

    private Long countEntriesWithSpecies(Class clazz, Object species) {
        String speciesString = species instanceof Species ? ((Species) species).getTaxId() : species.toString();
        if (StringUtils.isNumeric(speciesString)) {
            return schemaRepository.countEntriesWithSpeciesTaxId(clazz, speciesString);
        } else {
            return schemaRepository.countEntriesWithSpeciesName(clazz, speciesString);
        }
    }
}
