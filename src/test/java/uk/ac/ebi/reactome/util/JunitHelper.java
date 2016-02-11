package uk.ac.ebi.reactome.util;

import uk.ac.ebi.reactome.domain.model.DatabaseObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 28.01.16.
 */
public class JunitHelper {

    @SuppressWarnings("unchecked")
    public static void assertDatabaseObjectsEqual(DatabaseObject databaseObjectExpected, DatabaseObject databaseObjectObserved) throws InvocationTargetException, IllegalAccessException {
        Class clazz = databaseObjectExpected.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && !methodName.equals("getId") && !methodName.equals("getFollowingEvents")) {
                Object expected = method.invoke(databaseObjectExpected);
                Object observed = method.invoke(databaseObjectObserved);
                if (expected instanceof Collection) {
                    List<Object> expectedList = (List<Object>) expected;
                    List<Object> observedList = (List<Object>) observed;
                    assertEquals(expectedList.size(), observedList.size());
                    if (expectedList.size()>0) {
                        if (expectedList.get(0) instanceof DatabaseObject) {
                            List<DatabaseObject> expectedObjectList = (List<DatabaseObject>) expected;
                            List<DatabaseObject> observedObjectList = (List<DatabaseObject>) observed;
                            Collections.sort(expectedObjectList);
                            Collections.sort(observedObjectList);
                            for (int i = 0; i < expectedObjectList.size(); i++) {
                                DatabaseObject expectedValue = expectedObjectList.get(i);
                                DatabaseObject observedValue = observedObjectList.get(i);
//                              can not use equals here because expectedObject does not contain stableIdentifier
//                              assertTrue(expectedObject.equals(observedObject));
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
                    if(expected instanceof DatabaseObject) {
                        DatabaseObject expectedObject = (DatabaseObject) expected;
                        DatabaseObject observedObject = (DatabaseObject) observed;
                        assertEquals(expectedObject.getDbId(), observedObject.getDbId());
                        assertEquals(expectedObject.getDisplayName(), observedObject.getDisplayName());
                    } else {
                        assertEquals(expected,observed);
                    }
                }
            }
        }
    }
}
