package org.reactome.server.graph.util;

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

//    public static void assertDatabaseObjectsEqual(DatabaseObject databaseObjectExpected, DatabaseObject databaseObjectObserved) throws InvocationTargetException, IllegalAccessException {
//        assert databaseObjectExpected != null;
//        assert databaseObjectObserved != null;
//        Class clazzExpected = databaseObjectExpected.getClass();
//        Class clazzObserved = databaseObjectObserved.getClass();
//        assertEquals(clazzExpected,clazzObserved);
//        Method[] methods = clazzExpected.getMethods();
//        for (Method method : methods) {
//            if (method.getName().startsWith("get")) {
//                String fieldName = DatabaseObjectUtils.lowerFirst(method.getName().substring(3));
//                if (DatabaseObjectFactory.isValidAttribute(clazzExpected.getSimpleName(), fieldName)) {
//                    Object expected = method.invoke(databaseObjectExpected);
//                    Object observed = method.invoke(databaseObjectObserved);
//                    if (expected == null && observed == null) continue;
//                    assertFalse((expected == null) ^ (observed == null), "Assertion failed for field: " + fieldName + " either expected or observed was null");
//                    if (expected instanceof Collection) {
//                        //Needs to be cast to a Set to remove all duplicates of the expected list
//                        Set expectedSet = new HashSet((Collection) expected);
//                        Set observedSet = new HashSet((Collection) observed);
//                        assertEquals(expectedSet.size(), observedSet.size(), "Assertion failed for field: " + fieldName);
//                        assertEquals(expectedSet, observedSet, "Assertion failed for field: " + fieldName);
//                    } else {
//                        assertEquals(expected, observed, "Assertion failed for field: " + fieldName);
//                    }
//                }
//            }
//        }
//    }
}
