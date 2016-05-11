package org.reactome.server.graph.config;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
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
@ComponentScan(basePackages = "org.reactome.server.graph")
@EnableNeo4jRepositories(basePackages = "org.reactome.server.graph.repository")
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class Neo4jConfig extends Neo4jConfiguration {

    @Bean
    public Configuration getConfiguration() {
        Configuration config = new Configuration();
        config.driverConfiguration().setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
                .setURI(System.getProperty("neo4j.host")).setCredentials(System.getProperty("neo4j.user"),System.getProperty("neo4j.password"));
        return config;
    }

    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory(getConfiguration(), "org.reactome.server.graph.domain" );
    }

    @Bean
    @Scope(value = "prototype")
    public Session getSession() throws Exception {
        return super.getSession();
    }

}