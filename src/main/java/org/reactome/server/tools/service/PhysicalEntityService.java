package org.reactome.server.tools.service;

import org.reactome.server.tools.domain.model.PhysicalEntity;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 28.02.16.
 */
@SuppressWarnings("SameParameterValue")
public interface PhysicalEntityService  extends Service<PhysicalEntity>  {

    @SuppressWarnings("unused")
    PhysicalEntity findById(String id);
    PhysicalEntity findByDbId(Long dbId);
    PhysicalEntity findByStableIdentifier(String stableIdentifier);

    PhysicalEntity findByIdWithLegacyFields(String id);

}
