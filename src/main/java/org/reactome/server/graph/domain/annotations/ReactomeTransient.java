package org.reactome.server.graph.domain.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark all fields that should not be persisted during initial data import, but will be
 * used by the graph database later on.
 * This is done to avoid duplicated relationships. Example: inferred_to will create the initial relationship
 * inferredFrom will be annotated @ReactomeTransient to avoid creating the same relationship. Inferred_from will
 * reuse INCOMING the inferred_to relationship.
 *
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 01.03.16.
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReactomeTransient {}