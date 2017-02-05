<!--![Logo](https://cdn.evbuc.com/images/3621635/40070539972/1/logo.png)-->

# Reactome Graph Database

## What is the Reactome Graph project
 
The Reactome Graph Project models the [Reactome knowledgebase](http://www.reactome.org) into a interconnected graph.

At the cellular level, life is a network of molecular reactions. In Reactome, these processes are systematically described in molecular detail to generate an ordered network of molecular transformations (Fabregat et al. 2015). This amounts to millions of interconnected terms naturally forming a graph of biological knowledge. The Reactome Graph aims to provide an intuitive way for data retrieval as well as interpretation and analysis of pathway knowledge. 

Retrieving, and especially analyzing such complex data becomes tedious when using relational Databases. Queries across the pathway knowledgebase are composed by a number of expensive join operations resulting in bad performance and a hard-to-maintain project. Due to the schema based approach, relational databases are limited on how information will be stored and thus are difficult to be scaled for new requirements. 
In order to overcome these problems the Reactome database is imported in [Neo4j](http://neo4j.com), creating one big interconnected graph. The Graph database technology is an effective tool for modelling highly connected data. Since molecular networks are by their nature represented as a graph, storing Reactome data is in many ways beneficial: Firstly no normalization is required, data can be stored in its natural form. Secondly, nodes in the vicinity of a starting point can quickly be traversed, giving the user the possibility to not only retrieve data but perform fast analysis of these neighbor networks. Thus, knowledge can be retrieved that previously was not available due to the limitations of the relational data storage.

In the Reactome Graph project Neo4j, a Java implemented, open source, transactional database, with native graph storage and processing, is utilized. Neo4j offers multiple integration possibilities for Java development. In this project, [String Data Neo4j](http://projects.spring.io/spring-data-neo4j/)    provides automatic object graph mapping on top of Neo4j and integrates tightly with other parts of the spring framework used across the project.

#### main project components used:

* Spring Data Neo4j - version: 4.1.2.RELEASE
* AspectJ

#### Project usage: 

The project can be used as a "Core" library providing a data access layer on top of the Neo4j Db. To get started using the Graph Core include following dependency:

**Dependency** 

```
<dependency>
    <groupId>org.reactome.server.tools</groupId>
    <artifactId>graph-core</artifactId>
    <version>1.0.2</version>
</dependency>
```

**Repository**

```
<snapshotRepository>
    <uniqueVersion>false</uniqueVersion>
    <id>pst-snapshots</id>
    <name>EBI Nexus Snapshots Repository</name>
    <url>http://www.ebi.ac.uk/Tools/maven/repos/content/repositories/pst-release</url>
</snapshotRepository>
```

**Properties**

Connection to the Neo4j database can be configured using a properties file called "ogm.properties".

```
driver=org.neo4j.ogm.drivers.http.driver.HttpDriver
URI=http://user:password@localhost:7474
```

To provide properties programmatically, without the file Neo4jConfig.class of the Batch importer has to be overwritten and System properties have to be set:

```
@org.springframework.context.annotation.Configuration
@ComponentScan( basePackages = {"org.reactome.server.graph"} )
@EnableTransactionManagement
@EnableNeo4jRepositories( basePackages = {"org.reactome.server.graph.repository"} )
@EnableSpringConfigured
public class MyNeo4jConfig extends Neo4jConfig {

    @Bean
    public Configuration getConfiguration() {
        Configuration config = new Configuration();
        config.driverConfiguration()
                .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
                .setURI("http://".concat(System.getProperty("neo4j.host")).concat(":").concat(System.getProperty("neo4j.port")))
                .setCredentials(System.getProperty("neo4j.user"),System.getProperty("neo4j.password"));
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
 public static void main(String[] args) {

        System.setProperty("neo4j.host", "http://localhost:7474/");
        System.setProperty("neo4j.user", "neo4j");
        System.setProperty("neo4j.password", "reactome");
        
        ApplicationContext context = new AnnotationConfigApplicationContext(MyNeo4jConfig.class);
        GeneralService genericService = context.getBean(GeneralService.class);
        ... 
```

**Spring**

To enable the Graph Core library, when working with Spring, simply add the the following component-scan package to your mvc-dispatcher-servlet.xml. 

```
<context:component-scan base-package="org.reactome.server" />
```

Afterwards services can be autowired: 

```
@Autowired
private DatabaseObjectService service;
```

A full list of services and methods is available and can be found in our JAVA-Documentation todo link

**Lazy loading configuration**

The Graph core library provides lazy loading functionality. When using a getter of a relationship property, Objects will be loaded automatically if they are present in the Graph database. To deactivate this feature, which might be required in some cases: eg serializing results to json add the following bean to your configuration.
```
    <bean class="org.reactome.server.graph.aop.LazyFetchAspect" factory-method="aspectOf">
        <property name="enableAOP" value="false"/>
    </bean>
```

**Template projects**

Template projects for starting a project using the Graph core are found here: https://bitbucket.org/fkorn/graph-library-templates


#### Project Structure

The application follows a basic spring multi-tier architecture:

* Presentation Layer - Outside of the scope of this project. The projects acts as a data access layer for the content service. To see details about the content service goto https://github.com/reactome-pwp/content-service 
* Service Layer - Service and Spring Transactions.
* Persistence Layer - Provided by SpringDataNeo4j repositories and neo4jTemplate
* Database - Neo4j Standalone server
