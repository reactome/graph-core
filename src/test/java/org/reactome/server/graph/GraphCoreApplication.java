package org.reactome.server.graph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.reactome.server")
public class GraphCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphCoreApplication.class, args);
    }

}
