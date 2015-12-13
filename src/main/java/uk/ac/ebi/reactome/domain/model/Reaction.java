package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NodeEntity
public class Reaction extends ReactionLikeEvent {

    @Relationship
    private Reaction reverseReaction;
    private String totalProt;
    private String maxHomologues;
    private String inferredProt;
    @Relationship
    private List<Regulation> regulation;

    public Reaction() {
    }

    public List<Regulation> getRegulation() {
        return regulation;
    }

    public void setRegulation(List<Regulation> regulation) {
        this.regulation = regulation;
    }

    public String getTotalProt() {
        return this.totalProt;
    }

    public void setTotalProt(String totalProt) {
        this.totalProt = totalProt;
    }

    public String getMaxHomologues() {
        return this.maxHomologues;
    }

    public void setMaxHomologues(String maxHomologues) {
        this.maxHomologues = maxHomologues;
    }

    public String getInferredProt() {
        return this.inferredProt;
    }

    public void setInferredProt(String inferredProt) {
        this.inferredProt = inferredProt;
    }


    public Reaction getReverseReaction() {
        return reverseReaction;
    }


    public void setReverseReaction(Reaction reverseReaction) {
        this.reverseReaction = reverseReaction;
    }

}
