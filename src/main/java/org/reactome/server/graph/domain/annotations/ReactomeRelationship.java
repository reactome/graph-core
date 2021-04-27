package org.reactome.server.graph.domain.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark relationships added to improve graph traversing. Those
 * relationship using this annotation do not exist in the original relational database.
 *
 * This is currently used for data consistency check in the https://github.com/reactome/graph-importer
 *
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReactomeRelationship {
}
