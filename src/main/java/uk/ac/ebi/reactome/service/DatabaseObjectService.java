package uk.ac.ebi.reactome.service;

import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.domain.model.PhysicalEntity;
import uk.ac.ebi.reactome.domain.model.ReferenceEntity;
import uk.ac.ebi.reactome.domain.result.LabelsCount;
import uk.ac.ebi.reactome.domain.result.Participant;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
@SuppressWarnings("SameParameterValue")
public interface DatabaseObjectService extends Service<DatabaseObject> {

    @SuppressWarnings("unused")
    DatabaseObject findById(String id);
    DatabaseObject findByDbId(Long dbId);
    DatabaseObject findByDbIdNoRelations(Long dbId);
    DatabaseObject findByStableIdentifier(String stableIdentifier);

    Collection<ReferenceEntity> getParticipatingMolecules(Long dbId);
//    Collection<Participant> getParticipatingMolecules2(Long dbId);
    Collection<Participant> getParticipatingMolecules3(Long dbId);
    Collection<PhysicalEntity> getParticipatingMolecules4(String id);

    Collection<LabelsCount> getLabelsCount();
}
