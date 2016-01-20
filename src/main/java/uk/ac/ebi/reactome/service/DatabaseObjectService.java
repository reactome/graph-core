package uk.ac.ebi.reactome.service;

import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.domain.model.ReferenceEntity;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
public interface DatabaseObjectService extends Service<DatabaseObject> {

    DatabaseObject findOne(Long id, int depth);
    DatabaseObject findByDbId1(Long dbId);
    DatabaseObject findByDbId2(Long dbId);
    DatabaseObject findByStableIdentifier(String stableIdentifier);
    Collection<ReferenceEntity> getParticipatingMolecules(Long dbId);
}
