package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeTransient;
import org.reactome.server.graph.domain.result.DatabaseObjectLike;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * DatabaseObject contains the minimum fields used to define an instance of an Reactome entry
 * <p>
 * <p>
 * For the JsonIdentityInfo, when assigning generator as ObjectIdGenerators.PropertyGenerator could
 * slow down the json serialisation due to a paging problem. Right now the @JsonIgnore annotations
 * have been added to avoid serialising the not necessary attributes, but in case those are removed
 * the best thing is to remove the mentioned property
 */
@SuppressWarnings("unused")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "dbId")
@Node
public abstract class DatabaseObject implements Serializable, Comparable<DatabaseObject>, DatabaseObjectLike {

    @ReactomeTransient
    public transient Boolean isLoaded = false;

    @ReactomeTransient
    public transient Boolean preventLazyLoading = false;

//    @JsonIgnore
//    @Id @GeneratedValue
//    private Long id;

    @Id
    protected Long dbId;

    private String displayName;

    @ReactomeProperty(addedField = true)
    private String stId;

    private String stIdVersion;

    @JsonIgnore
    private transient String oldStId;

    @Relationship(type = "created", direction = Relationship.Direction.INCOMING)
    private InstanceEdit created;

    @Relationship(type = "modified", direction = Relationship.Direction.INCOMING)
    private InstanceEdit modified;

    public DatabaseObject() {
    }

    public DatabaseObject(Long dbId) {
        this.dbId = dbId;
    }

//    @ReactomeSchemaIgnore
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    @ReactomeSchemaIgnore
    public String getStIdVersion() {
        return stIdVersion;
    }

    public void setStIdVersion(String stIdVersion) {
        this.stIdVersion = stIdVersion;
    }

    @ReactomeSchemaIgnore
    public String getOldStId() {
        return oldStId;
    }

    public void setOldStId(String oldStId) {
        this.oldStId = oldStId;
    }

    public InstanceEdit getCreated() {
        return created;
    }

    public void setCreated(InstanceEdit created) {
        this.created = created;
    }

    public InstanceEdit getModified() {
        return modified;
    }

    public void setModified(InstanceEdit modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" +
                (stId == null ? "dbId=" + dbId : "dbId=" + dbId + ", stId='" + stId + '\'') +
                ", displayName='" + displayName + '\'' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatabaseObject that = (DatabaseObject) o;
        return dbId != null ? dbId.equals(that.dbId) : that.dbId == null && (stId != null ? stId.equals(that.stId) : that.stId == null && Objects.equals(displayName, that.displayName));
    }

    @Override
    public int hashCode() {
        int result = dbId != null ? dbId.hashCode() : 0;
        result = 31 * result + (stId != null ? stId.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(@NonNull DatabaseObject o) {
        return this.dbId.compareTo(o.dbId);
    }

    public final String getSchemaClass() {
        return getClass().getSimpleName();
    }

    public static DatabaseObject emptyObject() {
        return new Pathway();
    }

    @ReactomeSchemaIgnore
    @JsonIgnore
    public String getExplanation() {
        return "Not available";
    }

    @ReactomeSchemaIgnore //In some classes it is overridden to provide an easier-to-understand name
    public String getClassName() {
        return getClass().getSimpleName();
    }

    public <T> T fetchSingleValue(String methodName) {
        try {
            Method method = getClass().getMethod(methodName);
            //noinspection unchecked
            return (T) method.invoke(this);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    public <T> Collection<T> fetchMultiValue(String methodName) {
        try {
            Method method = this.getClass().getMethod(methodName);
            //noinspection unchecked
            return (Collection<T>) method.invoke(this);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return new ArrayList<>();
        }
    }

    @ReactomeSchemaIgnore
    @JsonIgnore
    public <T extends DatabaseObject> T preventLazyLoading() {
        return preventLazyLoading(true);
    }

    @SuppressWarnings({"unchecked", "WeakerAccess", "UnusedReturnValue"})
    @ReactomeSchemaIgnore
    @JsonIgnore
    public <T extends DatabaseObject> T preventLazyLoading(boolean preventLazyLoading) {
        if (this.preventLazyLoading == null) this.preventLazyLoading = false;
        if (this.preventLazyLoading == preventLazyLoading) return (T) this;

        this.preventLazyLoading = preventLazyLoading;

        //Here we go through all the getters and prevent LazyLoading for all the objects
        Method[] methods = getClass().getMethods();
        for (Method method : methods) {
            if (!method.getName().startsWith("get")) continue;
            try {
                Class<?> methodReturnClazz = method.getReturnType();

                if (DatabaseObject.class.isAssignableFrom(methodReturnClazz)) {
                    DatabaseObject object = (DatabaseObject) method.invoke(this);
                    if (object != null) {
                        if (object.preventLazyLoading == null) {
                            object.preventLazyLoading = false;
                        }
                        if (object.preventLazyLoading != preventLazyLoading) {
                            object.preventLazyLoading(preventLazyLoading);
                        }
                    }
                }

                if (Collection.class.isAssignableFrom(methodReturnClazz)) {
                    ParameterizedType stringListType = (ParameterizedType) method.getGenericReturnType();
                    Class<?> type = (Class<?>) stringListType.getActualTypeArguments()[0];
                    String clazz = type.getSimpleName();
                    if (DatabaseObject.class.isAssignableFrom(type)) {
                        Collection<T> collection = (Collection<T>) method.invoke(this);
                        if (collection != null) {
                            for (DatabaseObject obj : collection) {
                                DatabaseObject object = obj;
                                if (object != null) {
                                    if (object.preventLazyLoading == null) {
                                        object.preventLazyLoading = false;
                                    }
                                    if (object.preventLazyLoading != preventLazyLoading) {
                                        object.preventLazyLoading(preventLazyLoading);
                                    }
                                }
                            }
                        }
                    }
                }

            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return (T) this;
    }
}
