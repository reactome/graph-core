package org.reactome.server.graph.domain.result;

import org.neo4j.driver.Record;

import java.util.List;

@SuppressWarnings("unused")
public class Referrals {

    private String referral;
    private List<SimpleDatabaseObject> objects;

    public Referrals() {
    }

    public Referrals(String referral, List<SimpleDatabaseObject> objects) {
        this.referral = referral;
        this.objects = objects;
    }

    public static Referrals build(Record record) {
        return new Referrals(record.get("referral").asString(), record.get("objects").asList(SimpleDatabaseObject::build));
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public List<SimpleDatabaseObject> getObjects() {
        return objects;
    }

    public void setObjects(List<SimpleDatabaseObject> objects) {
        this.objects = objects;
    }
}
