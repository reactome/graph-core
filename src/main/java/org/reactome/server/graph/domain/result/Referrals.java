package org.reactome.server.graph.domain.result;

import org.reactome.server.graph.domain.model.DatabaseObject;

import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")

public class Referrals {

    private String referral;

    private List<DatabaseObject> objects;

    public Referrals() {
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public List<DatabaseObject> getObjects() {
        return objects;
    }

    public void setObjects(List<DatabaseObject> objects) {
        this.objects = objects;
    }
}
