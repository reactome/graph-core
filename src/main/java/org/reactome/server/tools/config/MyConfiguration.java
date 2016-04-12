package org.reactome.server.tools.config;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 25.01.16.
 *
 */
@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = "org.reactome.server.tools")
@EnableNeo4jRepositories(basePackages = "org.reactome.server.tools.repository")
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class MyConfiguration extends Neo4jConfiguration {

//    private @Value("${neo4j.host}") String neo4jHost;

//    private static final String NEO4J_HOST = "http://localhost:7474";
//    private static final int    NEO4J_PORT = ;

    @Bean
    public Configuration getConfiguration() {
        Configuration config = new Configuration();
        config.driverConfiguration().setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
                .setURI(System.getProperty("neo4j.host")).setCredentials(System.getProperty("neo4j.user"),System.getProperty("neo4j.password"));
        return config;
    }

    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory(getConfiguration(), "org.reactome.server.tools.domain" );
    }
}