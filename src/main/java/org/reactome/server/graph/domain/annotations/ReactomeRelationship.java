package org.reactome.server.graph.domain.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark relationships added to improve graph traversing. Those
 * relationship using this annotation do not exist in the original relational database. In that case, addedField should be true.
 *
 * It is also used to tell the name of the relationship in GKCentral using originName.
 *
 * This is currently used for data consistency check in the https://github.com/reactome/graph-importer
 *
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReactomeRelationship {
    boolean addedField() default false;
    String originName() default "";
}
