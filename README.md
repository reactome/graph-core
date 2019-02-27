[<img src=https://user-images.githubusercontent.com/6883670/31999264-976dfb86-b98a-11e7-9432-0316345a72ea.png height=75 />](https://reactome.org)

# Reactome Graph Database

## What is the Reactome Graph project
 
The Reactome Graph Project models the [Reactome knowledgebase](http://www.reactome.org) into a interconnected graph.

At the cellular level, life is a network of molecular reactions. In Reactome, these processes are systematically described in molecular detail to generate an ordered network of molecular transformations (Fabregat et al. 2015). This amounts to millions of interconnected terms naturally forming a graph of biological knowledge. The Reactome Graph aims to provide an intuitive way for data retrieval as well as interpretation and analysis of pathway knowledge. 

Retrieving, and especially analyzing such complex data becomes tedious when using relational Databases. Queries across the pathway knowledge base are composed by a number of expensive join operations resulting in bad performance and a hard-to-maintain project. Due to the schema based approach, relational databases are limited on how information will be stored and thus are difficult to be scaled for new requirements. 
In order to overcome these problems the Reactome database is imported in [Neo4j](http://neo4j.com), creating one big interconnected graph. The Graph database technology is an effective tool for modelling highly connected data. Since molecular networks are by their nature represented as a graph, storing Reactome data is in many ways beneficial: Firstly no normalization is required, data can be stored in its natural form. Secondly, nodes in the vicinity of a starting point can quickly be traversed, giving the user the possibility to not only retrieve data but perform fast analysis of these neighbor networks. Thus, knowledge can be retrieved that previously was not available due to the limitations of the relational data storage.

In the Reactome Graph project Neo4j, a Java implemented, open source, transactional database, with native graph storage and processing, is utilized. Neo4j offers multiple integration possibilities for Java development. In this project, [String Data Neo4j](http://projects.spring.io/spring-data-neo4j/) provides automatic object graph mapping on top of Neo4j and integrates tightly with other parts of the spring framework used across the project.

#### Main project components used:

* Spring Data Neo4j - version: 4.1.11.RELEASE
* AspectJ

## Project usage

The project can be used as a "Core" library providing a data access layer on top of the Neo4j Db. To get started using the Graph Core, please choose one of the following options:

* [Create new Java Standalone Project using Maven Archetype](https://github.com/reactome/graph-core#create-new-java-standalone-project-using-maven-archetype)
* [Add in an existing Java Standalone Project](https://github.com/reactome/graph-core#adding-the-graph-core-in-an-existing-project)
* [Add in a Spring based project](https://github.com/reactome/reactome/graph-core#add-in-a-spring-based-project)

### Create new Java Standalone Project using Maven Archetype 

We provide a Maven archetype as an easy handy way to create and set up a project that access the Reactome Graph Database using the graph-core. Please, go to [Graph Archetype](https://github.com/reactome/graph-archetype) page.

```console
mvn archetype:generate \ 
       -DarchetypeGroupId=org.reactome.maven.archetypes \ 
       -DarchetypeArtifactId=graph-archetype \
       -DarchetypeVersion=1.0.0 \
       -DgroupId=<your_group_id> \ 
       -DartifactId=<your \
       -Dversion=1.0.0-SNAPSHOT
       -DarchetypeRepository=http://www.ebi.ac.uk/Tools/maven/repos/content/repositories/pst-release/
```

### Adding the graph-core in an existing project

**Dependency** 

```html
<!-- http://www.ebi.ac.uk/Tools/maven/repos/content/repositories/pst-release/org/reactome/server/graph/graph-core/ for updates -->
<dependency>
    <groupId>org.reactome.server.graph</groupId>
    <artifactId>graph-core</artifactId>
    <version>1.1.18</dependency>
```

**Repository**

```html
<!-- EBI repo -->
<repository>
    <id>pst-release</id>
    <name>EBI Nexus Repository</name>
    <url>http://www.ebi.ac.uk/Tools/maven/repos/content/repositories/pst-release</url>
</repository>
```

**Properties**

Connection to the Neo4j database can be configured using a properties file called ```ogm.properties```.
The file can be added in ```${project}/src/main/resources``` 
```console
driver=org.neo4j.ogm.drivers.http.driver.HttpDriver
URI=http://user:password@localhost:7474
```

Following good practices, do no store passwords directly into a properties file. A Maven profile is strongly recommended and let Maven filter the resources into the properties file.
```console
driver=org.neo4j.ogm.drivers.http.driver.HttpDriver
URI=http://${neo4j.user}:${neo4j.password}@${neo4j.host}:${neo4j.port}
```

To provide properties programmatically, without the file Neo4jConfig.class of the Batch importer has to be overwritten and System properties have to be set:
```java
@org.springframework.context.annotation.Configuration
@ComponentScan( basePackages = {"org.reactome.server.graph"} )
@EnableTransactionManagement
@EnableNeo4jRepositories( basePackages = {"org.reactome.server.graph.repository"} )
@EnableSpringConfigured
public class MyNeo4jConfig extends Neo4jConfig {

    private SessionFactory sessionFactory;
    
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
        if (sessionFactory == null) {
            logger.info("Creating a Neo4j SessionFactory");
            sessionFactory = new SessionFactory(getConfiguration(), "org.reactome.server.graph.domain" );
        }
        return sessionFactory;
    }
}
```

Afterwards initialise the Reactome Graph Connection and get access the API 
```java
 public static void main(String[] args) {

        // Initialising ReactomeCore Neo4j configuration
        ReactomeGraphCore.initialise("localhost", "7474", "user", "password", MyNeo4jConfig.class);
    
        // Instanciate our services
        GeneralService genericService = ReactomeGraphCore.getService(GeneralService.class);
        DatabaseObjectService databaseObjectService = ReactomeGraphCore.getService(DatabaseObjectService.class);
        SpeciesService speciesService = ReactomeGraphCore.getService(SpeciesService.class);
        SchemaService schemaService = ReactomeGraphCore.getService(SchemaService.class);
        ... 
 }
```

## Add in a Spring based project

**Dependency** 

See above

**Repository**

See above

**Enabling graph-core Library**

To enable the Graph Core library, when working with Spring, simply add the the following component-scan package to your ```mvc-dispatcher-servlet.xml```. 

```xml
<context:component-scan base-package="org.reactome.server" />
```

Afterwards services can be autowired: 

```java
@Autowired
private DatabaseObjectService service;
```

**Lazy loading configuration**

Read more about [AspectJ and AOP](https://github.com/reactome/graph-core/tree/master/src/main/java/org/reactome/server/graph/aop/)

The Graph core library provides lazy loading functionality. When using a getter of a relationship property, Objects will be loaded automatically if they are present in the Graph database. To deactivate this feature, which might be required in some cases: e.g. serializing results to json, add the following bean to your configuration ```mvc-dispatcher-servlet.xml```. 
```xml
<bean class="org.reactome.server.graph.aop.LazyFetchAspect" factory-method="aspectOf">
    <property name="enableAOP" value="false"/>
</bean>
```

## Project Structure

The application follows a basic spring multi-tier architecture:

* Presentation Layer - Outside of the scope of this project. The projects acts as a data access layer for the content service. To see details about the content service goto https://github.com/reactome-pwp/content-service 
* Service Layer - Service and Spring Transactions.
* Persistence Layer - Provided by SpringDataNeo4j repositories and neo4jTemplate
* Database - Neo4j Standalone server
