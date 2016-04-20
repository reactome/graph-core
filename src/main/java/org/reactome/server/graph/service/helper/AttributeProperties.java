package org.reactome.server.graph.service.helper;

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
    private String valueType;
    private boolean declaredMethod;

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

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public boolean isDeclaredMethod() {
        return declaredMethod;
    }

    public void setDeclaredMethod(boolean declaredMethod) {
        this.declaredMethod = declaredMethod;
    }


    @Override
    public int compareTo(@Nonnull AttributeProperties o) {
        return this.name.compareTo(o.name);
    }
}
