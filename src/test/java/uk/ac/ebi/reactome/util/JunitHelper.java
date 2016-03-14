package uk.ac.ebi.reactome.util;

import org.neo4j.cypher.internal.compiler.v1_9.commands.expressions.Collect;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.domain.model.OpenSet;
import uk.ac.ebi.reactome.service.util.DatabaseObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 28.01.16.
 */
public class JunitHelper {

    @SuppressWarnings("unchecked")
    public static void assertDatabaseObjectsEqual(DatabaseObject databaseObjectExpected, DatabaseObject databaseObjectObserved) throws InvocationTargetException, IllegalAccessException {
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
                    if (expected instanceof Collection) {
                        List expectedList = new ArrayList((Collection) expected);
                        List observedList = new ArrayList((Collection) observed);
                        assertEquals(expectedList.size(), observedList.size());
                        if (expectedList.size() > 0) {
                            if (expectedList.get(0) instanceof DatabaseObject) {
                                List<DatabaseObject> expectedObjectList = (List<DatabaseObject>) expected;
                                List<DatabaseObject> observedObjectList = (List<DatabaseObject>) observed;
                                Collections.sort(expectedObjectList);
                                Collections.sort(observedObjectList);
                                for (int i = 0; i < expectedObjectList.size(); i++) {
                                    DatabaseObject expectedValue = expectedObjectList.get(i);
                                    DatabaseObject observedValue = observedObjectList.get(i);
                                    assertEquals(expectedValue.getDbId(), observedValue.getDbId());
                                    assertEquals(expectedValue.getDisplayName(), observedValue.getDisplayName());
                                }
                            } else {
                                for (int i = 0; i < expectedList.size(); i++) {
                                    Object expectedValue = expectedList.get(i);
                                    Object observedValue = observedList.get(i);
                                    assertEquals(expectedValue, observedValue);
                                }
                            }
                        }
                    } else {
                        if (expected instanceof DatabaseObject) {
                            DatabaseObject expectedObject = (DatabaseObject) expected;
                            DatabaseObject observedObject = (DatabaseObject) observed;
                            assertEquals(expectedObject.getDbId(), observedObject.getDbId());
                            assertEquals(expectedObject.getDisplayName(), observedObject.getDisplayName());
                        } else {
                            assertEquals(expected, observed);
                        }
                    }
                }
            }
        }
    }
}
