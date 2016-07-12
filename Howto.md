

Dependency to be included

```
    <!--Graph Core-->
    <dependency>
        <groupId>org.reactome.server.graph</groupId>
        <artifactId>graph-core</artifactId>
        <version>${graph.version}</version>
    </dependency>

```

Within a spring context:

if mvc-dispatcher-servlet.xml  add to your configuration
```
<context:component-scan base-package="org.reactome.server" />
```

to disable lazy loading for graph object add 
```
    <bean class="org.reactome.server.graph.aop.LazyFetchAspect" factory-method="aspectOf">
        <property name="enableAOP" value="false"/>
    </bean>
```
afterwards Service methods can be autowired 


If you dont completly rely on annotation driven Spring configuration: 









To work without the ogm.properties file: Need to Overwrite the Neo4jConfiguration of the graph core


```
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;
import org.reactome.server.graph.config.Neo4jConfig;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class MyNeo4jConfig extends Neo4jConfig {

    @Bean
    public Configuration getConfiguration() {
        Configuration config = new Configuration();
        config.driverConfiguration().setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
                .setURI(System.getProperty("neo4j.host")).setCredentials(System.getProperty("neo4j.user"),System.getProperty("neo4j.password"));
        return config;
    }

    @Override
    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory(getConfiguration(), "org.reactome.server.graph.domain" );
    }
}
```

Afterwards properties need to be set as System properties before loading the Application context 
```
 public static void main(String[] args) throws JSAPException {

        System.setProperty("neo4j.host", "http://localhost:7474/");
        System.setProperty("neo4j.user", "neo4j");
        System.setProperty("neo4j.password", "reactome");
        
        ApplicationContext context = new AnnotationConfigApplicationContext(MyNeo4jConfig.class);
        GeneralService genericService = context.getBean(GeneralService.class);
        ... 
```

