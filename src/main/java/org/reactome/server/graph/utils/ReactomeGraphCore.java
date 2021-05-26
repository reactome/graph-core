package org.reactome.server.graph.utils;

import org.reactome.server.graph.Main;
import org.reactome.server.graph.aop.LazyFetchAspect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

/**
 * This class aims to be used as a helper to set up the connection to the graph database
 * when this library is used by a stand-alone application.
 *
 * //First the class needs to be initialised
 * ReactomeGraphCore.initialise("uri","user","password", "dbName");
 *
 * //A certain Service can be retrieved as follows
 * GeneralService genericService = ReactomeGraphCore.getService(GeneralService.class);
 *
 * //And used as it is used here...
 * System.out.println("Database name: " + genericService.getDBName());
 */
@SuppressWarnings("unused")
public class ReactomeGraphCore {

    private static ApplicationContext context;

    public static void initialise(String uri, String user, String password) {
        initialise(uri, user, password, "graph.db");
    }

    public static void initialise(String uri, String user, String password, String databaseName) {
        System.setProperty("spring.neo4j.uri", uri);
        System.setProperty("spring.neo4j.authentication.username", user);
        System.setProperty("spring.neo4j.authentication.password", password);
        System.setProperty("spring.data.neo4j.database", databaseName);
        context = new AnnotationConfigApplicationContext(Main.class);
    }

    public static <T> T getService(Class<T> clazz) {
        if (context == null) throw new RuntimeException("ReactomeGraphCore has to be initialised!");
        if(clazz.getAnnotation(Service.class) != null) return context.getBean(clazz);
        throw new RuntimeException(clazz.getSimpleName() + " is not a Service");
    }

    public static void setLazyLoading(Boolean enabled){
        if (context == null) throw new RuntimeException("ReactomeGraphCore has to be initialised!");
        context.getBean(LazyFetchAspect.class).setEnableAOP(enabled);
    }
}
