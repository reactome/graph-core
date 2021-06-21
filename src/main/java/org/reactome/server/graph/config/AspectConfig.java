package org.reactome.server.graph.config;

import org.aspectj.lang.Aspects;
import org.reactome.server.graph.aop.LazyFetchAspect;
import org.reactome.server.graph.service.AdvancedDatabaseObjectService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConfig {

    @Bean
    public LazyFetchAspect lazyFetchAspectFilled(AdvancedDatabaseObjectService service) {
        LazyFetchAspect aspect = Aspects.aspectOf(LazyFetchAspect.class);
        aspect.setAdvancedDatabaseObjectService(service);
        return aspect;
    }
}
