package uk.ac.ebi.reactome;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.data.neo4j.template.Neo4jTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
@EnableNeo4jRepositories
@EnableTransactionManagement
public class Application extends Neo4jConfiguration{

//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
private  final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    /**
     * The main() method uses Spring Boot’s SpringApplication.run() method to launch an application.
     * @param args Neo4j username and password can be passed on the command line:
     *             mvn spring-boot:run -Drun.jvmArguments="-Dusername=user -Dpassword=password"
     */
    public static void main(String[] args) {
//        System.setProperty("username", "neo4j");
//        System.setProperty("password", "reactome");
        SpringApplication.run(Application.class, args);
    }


    /**
     * Neo4jServer interface provides a URL for access to the Database.
     * In SpringDataNeo4j 4.0 only RemoteServers can be returned
     * User and password can be specified as parameters
     * new RemoteServer("http://localhost:7474",username,password);
     *
     * @return new neo4jRemoteServer
     */
    @Override
    @Bean
    public Neo4jServer neo4jServer() {
        System.setProperty("username", "neo4j");
        System.setProperty("password", "reactome");
        logger.info("Initialising server connection");
        return new RemoteServer("http://localhost:7474");
    }

    /**
     * The SessionFactory is needed to create instances of org.neo4j.ogm.session.Session.
     *
     * @return new Neo4jSessionFactory
     */
    @Override
    @Bean
    public SessionFactory getSessionFactory() {
        logger.info("Initialising Session Factory");
        return new SessionFactory("uk.ac.ebi.reactome.domain");
    }

    /**
     * Repositories and Neo4jTemplate are Session driven.
     * By Autowiring the Session can be used in the code directly if needed.
     *
     * @return Session
     * @throws Exception (unspecified)
     */
    @Override
    @Bean
    public Session getSession() throws Exception {
        logger.info("Initialising session-scoped Session Bean");
        return super.getSession();
    }

    /**
     * Spring Data Neo4j offers a Neo4jTemplate for interacting with the Neo4jDatabase which can be used
     * additionally to repositories
     * Neo4jTemplate is based on org.neo4j.ogm.session.Session. It adds an additional layer on top of the session,
     * handles transactions and provides exception translation.
     * Neo4jTemplate offers implicit transactions (auto commits) for methods: getOrCreate delete query
     * For other Methods Spring Transaction management of @Transactional can be used
     * Interface is called Neo4jOperations
     *
     * @return default neo4jTemplate
     * @throws Exception (unspecified)
     */
    @Override
    @Bean
    public Neo4jOperations neo4jTemplate() throws Exception {
        return new Neo4jTemplate(getSession());
    }


}