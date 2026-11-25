package org.reactome.server.graph.domain;

import org.junit.jupiter.api.Test;
import org.neo4j.driver.Values;
import org.reactome.server.graph.domain.model.Person;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ReflectionUtils fills an object's fields from a query's answer. A class's constants, such as
 * serialVersionUID, are not among them: a primitive one would be refused, and the refusal
 * printed, for every object built. Needs no graph.
 */
class ReflectionUtilsTest {

    @Test
    void fillsTheFieldsAndLeavesTheConstantsAlone() {
        PrintStream err = System.err;
        ByteArrayOutputStream printed = new ByteArrayOutputStream();
        System.setErr(new PrintStream(printed, true, StandardCharsets.UTF_8));
        Person person;
        try {
            person = ReflectionUtils.build(new Person(), Values.value(Map.of("dbId", 1L, "surname", "Placeholder", "firstname", "A")));
        } finally {
            System.setErr(err);
        }
        assertEquals(1L, person.getDbId());
        assertEquals("Placeholder", person.getSurname());
        assertEquals("A", person.getFirstname());
        assertEquals("", printed.toString(StandardCharsets.UTF_8));
    }
}
