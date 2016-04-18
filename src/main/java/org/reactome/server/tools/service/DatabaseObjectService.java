package org.reactome.server.tools.service;

import org.reactome.server.tools.domain.model.DatabaseObject;
import org.reactome.server.tools.domain.model.PhysicalEntity;
import org.reactome.server.tools.domain.result.LabelsCount;
import org.reactome.server.tools.domain.model.ReferenceEntity;
import org.reactome.server.tools.domain.result.Participant;
import org.reactome.server.tools.domain.result.ComponentOf;

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
    Collection<Participant> getParticipatingMolecules2(Long dbId);
    Collection<PhysicalEntity> getParticipatingMolecules3(String id);

    Collection<LabelsCount> getLabelsCount();


    Collection<PhysicalEntity> getOtherFormsOfThisMolecule(Long dbId);


    Collection<ComponentOf> getComponentsOf(String stableIdentifier);
}
