package org.reactome.server.graph.config;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.reactome.server.graph.aop.LazyFetchAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Guilherme Viteri (gviteri@ebi.ac.uk)
 */
@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = "org.reactome.server.graph")
@EnableNeo4jRepositories(basePackages = "org.reactome.server.graph.repository")
@EnableTransactionManagement
@EnableSpringConfigured
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
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Session getSession() throws Exception {
        return super.getSession();
    }

    @Bean
    public LazyFetchAspect lazyFetchAspect() {
        return org.aspectj.lang.Aspects.aspectOf(LazyFetchAspect.class);
    }

}