package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Reaction extends ReactionLikeEvent {

//    private String totalProt;
//    private String maxHomologues;
//    private String inferredProt;

    @Relationship(type = "reverseReaction")
    private Reaction reverseReaction;


//    @Relationship(type = "regulation")
//    private List<Regulation> regulation;

    public Reaction() {}
//
//    public String getTotalProt() {
//        return totalProt;
//    }
//
//    public void setTotalProt(String totalProt) {
//        this.totalProt = totalProt;
//    }
//
//    public String getMaxHomologues() {
//        return maxHomologues;
//    }
//
//    public void setMaxHomologues(String maxHomologues) {
//        this.maxHomologues = maxHomologues;
//    }
//
//    public String getInferredProt() {
//        return inferredProt;
//    }
//
//    public void setInferredProt(String inferredProt) {
//        this.inferredProt = inferredProt;
//    }

    public Reaction getReverseReaction() {
        return reverseReaction;
    }

    public void setReverseReaction(Reaction reverseReaction) {
        this.reverseReaction = reverseReaction;
    }

//    public List<Regulation> getRegulation() {
//        return regulation;
//    }
//
//    public void setRegulation(List<Regulation> regulation) {
//        this.regulation = regulation;
//    }
}
