package org.reactome.server.graph.service.helper;

import org.reactome.server.graph.domain.model.DatabaseObject;

import javax.annotation.Nonnull;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 11.02.16.
 */
@SuppressWarnings("unused")
public class AttributeProperties implements Comparable<AttributeProperties> {

    private String name;
    private String cardinality;
    private Class valueType;
    private boolean valueTypeDatabaseObject;
    private Class<? extends DatabaseObject> origin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardinality() {
        return cardinality;
    }

    public void setCardinality(String cardinality) {
        this.cardinality = cardinality;
    }

    public Class getValueType() {
        return valueType;
    }

    public void setValueType(Class valueType) {
        this.valueType = valueType;
    }

    public Class<? extends DatabaseObject> getOrigin() {
        return origin;
    }

    public void setOrigin(Class<? extends DatabaseObject> origin) {
        this.origin = origin;
    }

    public boolean isValueTypeDatabaseObject() {
        if(DatabaseObject.class.isAssignableFrom(valueType)) {
            setValueTypeDatabaseObject(true);
        }
        return valueTypeDatabaseObject;
    }

    public void setValueTypeDatabaseObject(boolean valueTypeDatabaseObject) {
        this.valueTypeDatabaseObject = valueTypeDatabaseObject;
    }

    @Override
    public int compareTo(@Nonnull AttributeProperties o) {
        return this.name.compareTo(o.name);
    }
}
