package uk.ac.ebi.reactome.config;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
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
@ComponentScan(basePackages = "uk.ac.ebi.reactome")
@EnableNeo4jRepositories(basePackages = "uk.ac.ebi.reactome.repository")
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class MyConfiguration extends Neo4jConfiguration {

    private static final String NEO4J_HOST = "http://localhost:";
    private static final int    NEO4J_PORT = 7474;

    @Bean
    public Configuration getConfiguration() {
        Configuration config = new Configuration();
        config.driverConfiguration().setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
                .setURI(NEO4J_HOST + NEO4J_PORT).setCredentials(System.getProperty("neo4j.user"),System.getProperty("neo4j.password"));
        return config;
    }

    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory(getConfiguration(), "uk.ac.ebi.reactome.domain" );
    }
}