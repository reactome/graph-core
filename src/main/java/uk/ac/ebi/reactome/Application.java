package uk.ac.ebi.reactome;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uk.ac.ebi.reactome.domain.result.LabelsCount;
import uk.ac.ebi.reactome.repository.DatabaseObjectRepository;

import java.util.Collection;

/**
 * @since 27 October 2015
 * @author Florian Korninger (fkorn@ebi.ac.uk)
 * @version 1.0
 *
 * Spring boot is used to bootstrap and launch a Spring application from a Java main method.
 * It will perform the following steps to bootstrap your application:
 *      Create an appropriate ApplicationContext instance (depending on your classpath)
 *      Register a CommandLinePropertySource to expose command line arguments as Spring properties
 *      Refresh the application context, loading all singleton beans
 *
 * Neo4j Configuration is done by overriding Neo4jServer and SessionFactory. As Beans they are accessible to spring.
 * Connecting to Neo4j is handled by spring.
 * Session and Neo4j Template can be used additionally to SpringGraphRepositories
 *
 * Credentials are needed for Neo4j 2.2 or later:
 * Neo4j username and password can be passed on the command line:
 *      mvn spring-boot:run -Drun.jvmArguments="-Dusername=user -Dpassword=password"
 */


public class Application {

private static final Logger logger = Logger.getLogger(Application.class);


    /**
     * The main() method uses Spring Boot’s SpringApplication.run() method to launch an application.
     * @param args Neo4j username and password can be passed on the command line:
     *             mvn spring-boot:run -Drun.jvmArguments="-Dusername=user -Dpassword=password"
     */

;

    public static void main(String[] args) {


        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfiguration.class);
//        As the @Autowired annotation doesn't work in static context,
//        we're using context.getBean(...) here instead.

        DatabaseObjectRepository repo = ctx.getBean(DatabaseObjectRepository.class);
        Collection<LabelsCount> x= repo.getLabelsCount();
        System.out.println();

    }


}