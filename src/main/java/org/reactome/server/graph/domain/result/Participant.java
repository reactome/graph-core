package org.reactome.server.graph.domain.result;

import org.neo4j.driver.Record;
import org.neo4j.driver.Value;

import java.util.Collection;

/**
 * Object for retrieving Ewas and their ReferenceEntities for a given Pathway Id
 */
@SuppressWarnings("unused")
public class Participant {

    private Long peDbId;
    private String displayName;
    private String schemaClass;
    private Collection<ParticipantRefEntities> refEntities;

    public Participant() {
    }

    public static Participant build(Record record) {
        Participant participant = new Participant();
        participant.setPeDbId(record.get("peDbId").asLong());
        participant.setDisplayName(record.get("displayName").asString(null));
        participant.setSchemaClass(record.get("schemaClass").asString(null));
        participant.setRefEntities(record.get("refEntities").asList(ParticipantRefEntities::build));
        return participant;
    }

    public Long getPeDbId() {
        return peDbId;
    }

    public void setPeDbId(Long peDbId) {
        this.peDbId = peDbId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSchemaClass() {
        return schemaClass;
    }

    public void setSchemaClass(String schemaClass) {
        this.schemaClass = schemaClass;
    }

    public Collection<ParticipantRefEntities> getRefEntities() {
        return refEntities;
    }

    public void setRefEntities(Collection<ParticipantRefEntities> refEntities) {
        this.refEntities = refEntities;
    }

    private static class ParticipantRefEntities {
        private Long dbId;
        private String identifier;
        private String schemaClass;
        private String displayName;
        private String icon;
        private String url;

        public static ParticipantRefEntities build(Value value) {
            ParticipantRefEntities participantRefEntities = new ParticipantRefEntities();
            participantRefEntities.setDbId(value.get("dbId").asLong());
            participantRefEntities.setIdentifier(value.get("identifier").asString());
            participantRefEntities.setSchemaClass(value.get("schemaClass").asString());
            participantRefEntities.setDisplayName(value.get("displayName").asString());
            participantRefEntities.setIcon(value.get("icon").asString());
            participantRefEntities.setUrl(value.get("url").asString());
            return participantRefEntities;
        }

        public Long getDbId() {
            return dbId;
        }

        public void setDbId(Long dbId) {
            this.dbId = dbId;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getSchemaClass() {
            return schemaClass;
        }

        public void setSchemaClass(String schemaClass) {
            this.schemaClass = schemaClass;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
