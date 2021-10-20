package org.reactome.server.graph.service.helper;

import org.reactome.server.graph.domain.model.DatabaseObject;

public class AttributeClass {

    private Class type;
    private boolean valueTypeDatabaseObject;

    public AttributeClass(Class type) {
        this.type = type;
        valueTypeDatabaseObject = DatabaseObject.class.isAssignableFrom(type);
    }

    public Class getType() {
        return type;
    }

    public boolean isValueTypeDatabaseObject() {
        return valueTypeDatabaseObject;
    }
}
