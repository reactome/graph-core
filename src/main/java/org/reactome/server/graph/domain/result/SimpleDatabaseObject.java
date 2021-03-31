//package org.reactome.server.graph.domain.result;
//
//import org.reactome.server.graph.service.util.DatabaseObjectUtils;
//
//
//import java.util.Collection;
//
///**
// * @author Antonio Fabregat <fabregat@ebi.ac.uk>
// */
//@SuppressWarnings("unused")
//
//public class SimpleDatabaseObject implements DatabaseObjectLike {
//
//    private Long dbId;
//
//    private String stId;
//
//    private String displayName;
//
//    private String schemaClass;
//
//    public SimpleDatabaseObject() {}
//
//    public Long getDbId() {
//        return dbId;
//    }
//
//    public void setDbId(Long dbId) {
//        this.dbId = dbId;
//    }
//
//    public String getStId() {
//        return stId;
//    }
//
//    public void setStId(String stId) {
//        this.stId = stId;
//    }
//
//    public String getDisplayName() {
//        return displayName;
//    }
//
//    public void setDisplayName(String displayName) {
//        this.displayName = displayName;
//    }
//
//    public String getSchemaClass() {
//        return schemaClass;
//    }
//
//    public void setLabels(Collection<String> labels) {
//        this.schemaClass = DatabaseObjectUtils.getSchemaClass(labels);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        SimpleDatabaseObject that = (SimpleDatabaseObject) o;
//
//        return dbId != null ? dbId.equals(that.dbId) : that.dbId == null;
//    }
//
//    @Override
//    public int hashCode() {
//        return dbId != null ? dbId.hashCode() : 0;
//    }
//}
