package uk.ac.ebi.reactome.domain.result;

import org.springframework.data.neo4j.annotation.QueryResult;
import uk.ac.ebi.reactome.domain.model.ReferenceEntity;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 03.02.16.
 */
@QueryResult
public class Participant2 {

    private Long ewasDbId;
    private String ewasName;
    private Collection<ReferenceEntity> referenceEntities;

    public Participant2() {
    }

    public Long getEwasDbId() {
        return ewasDbId;
    }

    public void setEwasDbId(Long ewasDbId) {
        this.ewasDbId = ewasDbId;
    }

    public String getEwasName() {
        return ewasName;
    }

    public void setEwasName(String ewasName) {
        this.ewasName = ewasName;
    }

    public Collection<ReferenceEntity> getReferenceEntities() {
        return referenceEntities;
    }

    public void setReferenceEntities(Collection<ReferenceEntity> referenceEntities) {
        this.referenceEntities = referenceEntities;
    }
}
