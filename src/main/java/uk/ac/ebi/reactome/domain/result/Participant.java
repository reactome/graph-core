package uk.ac.ebi.reactome.domain.result;

import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 28.01.16.
 */
@QueryResult
public class Participant {

    private Long ewasDbId;
    private String ewasName;
//    private Collection<Long> refEntitiesDbIds;
//    private Collection<String> refEntitiesNames;

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

//    public Collection<Long> getRefEntitiesDbIds() {
//        return refEntitiesDbIds;
//    }
//
//    public void setRefEntitiesDbIds(Collection<Long> refEntitiesDbIds) {
//        this.refEntitiesDbIds = refEntitiesDbIds;
//    }
//
//    public Collection<String> getRefEntitiesNames() {
//        return refEntitiesNames;
//    }
//
//    public void setRefEntitiesNames(Collection<String> refEntitiesNames) {
//        this.refEntitiesNames = refEntitiesNames;
//    }
}
