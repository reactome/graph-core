package org.reactome.server.graph.domain.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark primitive fields that should be filled during initial data import.
 *
 * @author Florian Korninger &lt;florian.korninger@ebi.ac.uk&gt;
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReactomeProperty {

    boolean addedField() default false;

    String originName() default "";
}
