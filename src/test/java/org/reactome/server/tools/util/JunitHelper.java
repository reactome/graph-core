package org.reactome.server.tools.util;

import org.reactome.server.tools.domain.model.DatabaseObject;
import org.reactome.server.tools.service.util.DatabaseObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Provides helper method for testing equality of two Database Objects. All get Methods that are valid GkInstance fields will be tested
 * AssertEquals will utilize the equals method implemented in the DatabaseObject. It will check the equality of the dbId, stId and
 * DisplayName.
 *
 * Created by:
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 28.01.16.
 */
public class JunitHelper {

    @SuppressWarnings("unchecked")
    public static void assertDatabaseObjectsEqual(DatabaseObject databaseObjectExpected, DatabaseObject databaseObjectObserved) throws InvocationTargetException, IllegalAccessException {
        assert databaseObjectExpected != null;
        assert databaseObjectObserved != null;
        Class clazzExpected = databaseObjectExpected.getClass();
        Class clazzObserved = databaseObjectObserved.getClass();
        assertEquals(clazzExpected,clazzObserved);
        Method[] methods = clazzExpected.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                String fieldName = DatabaseObjectUtils.lowerFirst(method.getName().substring(3));
                if (DatabaseObjectFactory.isValidAttribute(clazzExpected.getSimpleName(), fieldName)) {
                    Object expected = method.invoke(databaseObjectExpected);
                    Object observed = method.invoke(databaseObjectObserved);
                    if (expected == null && observed == null) continue;
                    assertFalse("Assertion failed for field: " + fieldName + " either expected or observed was null",
                            (expected == null) ^ (observed == null));
                    if (expected instanceof Collection) {
                        //Needs to be cast to a Set to remove all duplicates of the expected list
                        Set expectedSet = new HashSet((Collection) expected);
                        Set observedSet = new HashSet((Collection) observed);
                        assertEquals("Assertion failed for field: " + fieldName, expectedSet.size(), observedSet.size());
                        assertEquals("Assertion failed for field: " + fieldName, expectedSet, observedSet);
                    } else {
                        assertEquals("Assertion failed for field: " + fieldName, expected, observed);
                    }
                }
            }
        }
    }
}
