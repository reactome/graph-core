package org.reactome.server.graph;

import org.aspectj.lang.Aspects;
import org.reactome.server.graph.aop.LazyFetchAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableNeo4jRepositories(basePackages = "org.reactome.server.graph.repository")
@EnableTransactionManagement
@EnableSpringConfigured
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    /**
     * This is needed to get hold of the instance of the aspect which is created outside of the spring container,
     * and make it available for autowiring.
     */
    @Bean LazyFetchAspect lazyFetchAspect() {
        return Aspects.aspectOf(LazyFetchAspect.class);
    }
}