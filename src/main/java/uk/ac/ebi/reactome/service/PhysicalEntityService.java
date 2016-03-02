package uk.ac.ebi.reactome.service;

import uk.ac.ebi.reactome.domain.model.PhysicalEntity;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 28.02.16.
 */
public interface PhysicalEntityService  extends Service<PhysicalEntity>  {

    PhysicalEntity findById(String id);
    PhysicalEntity findByDbId(Long dbId);
    PhysicalEntity findByStableIdentifier(String stableIdentifier);

    PhysicalEntity findByIdWithLegacyFields(String id);

}
