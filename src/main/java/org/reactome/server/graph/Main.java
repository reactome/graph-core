package org.reactome.server.graph;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.repository.DatabaseObjectRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@Configuration
@EnableNeo4jRepositories(basePackages = "org.reactome.server.graph.repository")
@EnableTransactionManagement
@EnableSpringConfigured
public class Main {

    public static void main(String[] args) {

        ApplicationContext aa = SpringApplication.run(Main.class, args);
        for (String name: aa.getBeanDefinitionNames()) {
            //System.out.println(name);
        }

        DatabaseObjectRepository aaa = aa.getBean(DatabaseObjectRepository.class);
        DatabaseObject b = aaa.findByStId("R-HSA-69620");
        Pathway pp = (Pathway)b;

        System.out.println(pp.getCompartment());

    }

}