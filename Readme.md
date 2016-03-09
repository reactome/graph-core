![Logo](https://cdn.evbuc.com/images/3621635/40070539972/1/logo.png)

# Reactome Graph Database

## What is the Reactome Graph Database
The Reactome Graph Project 
 
 
 iology are all examples of applications that can be represented in a
 much more natural form. Comparisons will be drawn between relational database systems (Oracle, MySQL) and graph
 databases (Neo4J) focusing on aspects such as data structures, data model features and query facilities. Additionally, several
 of the inherent and contemporary limitations of current offerings comparing and contrasting graph vs. relational database
 implementations will be explored.
 
 
 The relational database model has been around since the late 1960s [4]. It has proven to consistently provide persistence,
 concurrency control, and integration mechanisms. Relational databases maintain tables which are defined by sets of rows and
 columns. A row can be perceived as an object while columns would be attributes/properties of that objects [15]. One of the
 weaknesses of the relational model is its limited ability to explicitly capture requirement semantics [14]. Big data problems
 involving complex interconnected information have become increasingly common in the sciences. Storing, retrieving, and
 manipulating such complex data becomes onerous when using traditional RDBMS approaches. Schema based data models by
 their very definition put in place limits on how information will be stored. There is an involved manual process to redesign
 the schema in order to adapt to new data. Where the RDBMS is optimized for aggregated data, graph databases such as
 Neo4j are optimized for highly connected data.
 A graph is a data structure composed of edges and vertices [2]. Graph database technology is an effective tool for modeling
 data when a focus on the relationship between entities is a driving force in the design of a data model [3]. Modeling objects
 and the relationships between them means almost anything can be represented in a corresponding graph. A common graph
 type supported by most systems is the property graph. Property graphs are attributed, labeled, directed multi-graphs [2].
 Figure 1 provides a visual example of a property graph which represents interactions between people and objects. A benefit
 to the multi graph is that it is the most complex implementation because every other type of graph consists of subsets of the
 property graph implementation. This means a property graph can effectively model all other graph types. The graph database
 is optimized for the efficient processing of dense, interrelated datasets [2]. This design allows the construction of predictive
 models, and detection of correlations and patterns [3]. This highly dynamic data model in which all nodes are connected by
 relations allows for fast traversals along the edges between vertices. A particular benefit is the fact that traversals are
 localized and do not have to take into account sets of unrelated data. A problem that is inherent in SQL [15].
 
 
 Graphs are ubiquitous in bioinformatics and frequently consist
 of too many nodes and edges to represent in random access
 memory. These graphs are thus stored in databases to allow
 for efficient queries using declarative query languages such as
 Structured Query Language (SQL). Traditional relational data-
 bases (e.g. MySQL and PostgreSQL) have long been used for
 this purpose and are based on decades of research into query
 optimization. Recently, NoSQL databases have caught a lot of
 attention because of their advantages in scalability. The term
 NoSQL is used to refer to schemaless databases such as key/
 value stores (e.g. Apache Cassandra), document stores
 (e.g. MongoDB) and graph databases (e.g. AllegroGraph,
 Neo4J, OpenLink Virtuoso), which do not fit within the trad-
 itional relational paradigm. Most NoSQL databases do not have
 a declarative query language. The widely used Neo4J graph data-
 base is an exception (Webber et al., 2013). Its query language
 Cypher is designed for expressing graph queries, but is still
 evolving.
 Graph databases have so far seen only limited use within bio-
 informatics
 
 A common task in STRING is to retrieve a neighbor network.
 This involves finding the immediate neighbors of a protein and
 all interactions between them. To express this as a single SQL
 query requires the use of query nesting and a UNION set oper-
 ation. Because Cypher currently supports neither of these fea-
 tures, two queries are needed to solve the task: one to find
 immediate neighbors and a second to find their interactions,
 which must be run for each of the immediate neighbors.
 Although this precludes some query optimizations, running all
 these Cypher queries is 36Â faster than running the single SQL
 query (Table 1). However, it should be noted that a 49Â fold
 speedup is attainable with PostgreSQL by similarly decomposing
 the complex query into multiple simple SQL queries. In theory,
 posing the task as one declarative query maximizes the oppor-
 tunity for query optimization, but in practice this does not
 always give good performance. These results also show that
 even for graph data, using a graph database is not necessarily
 an advantage.
 Finding the best scoring path in a weighted graph is another
 frequently occurring task. For example, finding the best scoring
 path connecting two proteins in the STRING network is a cru-
 cial part of the NetworKIN algorithm (Linding et al., 2007). This
 task can be expressed single query both in (recursive) SQL and in
 Cypher. However, in practice neither query can be executed
 unless the maximal path length is severely constrained, in
 which case the Cypher query was faster by a factor of 981Â
 (Table 1). The poor scalability is because of an exponential
 explosion in the number of longer paths, which in part is because
 
 
 is aiming to provide information about protein interactions for Reactome data.
This project is micro-service based web-application built using following components:
* Spring Data Neo4j 
* Neo4j


#### Setup
The application will attempt to use a Neo4j standalone server running on standard port:7474.

#### Installing Neo4j
Neo4j can be installed in the command line

```
wget -O - https://debian.neo4j.org/neotechnology.gpg.key| apt-key add -
echo 'deb http://debian.neo4j.org/repo stable/' > /etc/apt/sources.list.d/neo4j.list
aptitude update
aptitude install neo4j
```

Neo4j will be installed to folders
```
/etc/neo4j  (properties)
/usr/share/neo4j (library)
/var/lib/neo4j (data)
```

!!! CAUTION  !!!
http://neo4j.com/docs/stable/server-installation.html
This approach to running Neo4j as a server is deprecated. We strongly advise you to run Neo4j from a package where feasible.
You can build your own init.d script. See for instance the Linux Standard Base specification on system initialization, or one of the many samples and tutorials.
http://refspecs.linuxfoundation.org/LSB_3.1.0/LSB-Core-generic/LSB-Core-generic/tocsysinit.html
http://www.linux.com/learn/tutorials/442412-managing-linux-daemons-with-init-scripts

#### Neo4j Shell commands

Neo4j can be controlled in the command line.

```
sudo service neo4j-service start
sudo service neo4j-service stop
sudo service neo4j-service status
sudo service neo4j-service restart
sudo service neo4j-service info
```

#### Starting the application
The application can be started using:
```
spring-boot:run
```

#### Neo4j Authentication

Neo4j Server is bundled with a Web server answering only requests from the local machine. This behavior can be changed in ```/conf/neo4j-server.properties```.
Neo4j requires clients to supply authentication credentials. The authentication data is stored under ```/var/lib/neo4j/data/dbms/auth```.
Authentication can also be changed in the Neo4j-Browser with the command:
```
:server change-password
```

#### Authentication in the application

Authentication has to be provided either by setting System arguments or passing them in command line:
```
spring-boot:run "-Drun.jvmArguments=-Dusername=user -Dpassword=password
```
#### Loading the initial dataset

Currently the initial Reactome data set can be loaded using the REST-request
```
http://localhost:8080/load
```

#### API

Loading Data:

If creating a new index it is advised to delete all data from the Database:
```
http://localhost:8080/cleanDb
```
Load entire ReactomeDataSet
```
http://localhost:8080/loadAll
```
Constraints can be created using:
```
http://localhost:8080/createConstraints
```
Load a singe Instance for testing 
```
http://localhost:8080/load/stId
http://localhost:8080/load/dbId
```

Intact
```
http://localhost:8080/loadIntact
```


TODO



#### Logging
AOP is 

To see more spring behaviour (e.g. neo4jRequestHandler, connectionPooling ...) set Spring root logging level to DEBUG 
```
logging.level.=DEBUG
```

#### Project Structure
The application follows a basic spring multitier architecture:
<ul>
<li>Presentation Layer - SpringMVC</li>
<li>Service Layer - Service and Spring Transactions </li>
<li>Persistance Layer - partly provided by SpringData repositories</li>
<li>Database - Neo4j</li>
</ul>

#### SpringDataNeo4j-4 (SND4)
SDN4 (version 4.0.0) has been rewritten to support Neo4j as a standalone server.
It utilizes Cypher and HTTP to communicate with the database.

SDN4 provides among other things:
<ul>
<li>Object-Graph-Mapping of annotated POJO entities</li>
<li>Repository infrastructure. Repositories provide commonly used persistance methods and can be extended by annotated, named or derived "finder" methods</li>
</ul>

#### SDN4 Problems
Since SDN4 has been rewritten recently there are still issues that caused problems and features that are missing.

Problems occurred in this project
<ul>
<li>SDN4 is currently unable to persist rich relationships and will throw an Nullpointer exception depending on the number of relationships and saving depths. This can be avoided by reducing the saving depth. This is currently a JIRA issue in SDN4 and will be fixed with version 4.0.1</li>
<li> Undirected or Cyclist Relationships will go into an infinite loop in the objects returned. This is currently a JIRA issue in SDN4 and will be fixed with version 4.0.1</li>
<li>Collections are not supported as relationship container in POJOs. Using Collections will result in losing data! It is recommended to always use Sets.</li>
</ul>

Missing features and current "workarounds"
<ul>
<li>SDN4 approach is quite similar to e.g. hibernate. Instead of Cascading and Lazy or Eager loading it is possible to specify search or saving depth. The proble is that the CRUD methods provided by SDN4 are entirely based on the GraphId. The derived "finder" methods do not support a depth parameter. By default depth is defined as 1. This is planned to be implemented for future versions. </li>
<li>Workarounds: 
<ul>
<li>```findOne(graphId,depth)``` where the GraphId is retrieved by an additional query to neo4j with a derived finder method.</li>
<li>```Session.loadall(class, new Filter ("property","propertyValue"),depth)```
</ul>
</li>
<li>Currently SDN4 does not offer any Merge method. This is especially a hindrance since the uniqueness of an entity is ensured by the automatically created GraphId. In a project where entries are based on an external identifier(dbId) save cannot be an option. To avoid this problem the first option is to write a merge method in cypher (similar to the one shown below). The second is the use of a combination of find and save methods. With both options cascading saves (saves with depth > 0) will be a problem.</li>
</ul>

## SDN4

#### Object Graph Mapping 
When an entity is constructed from a node or relationship OGM comes into play.The framework will attempt to map fields of an object to node properties or related nodes. SDN4 supports mapping annotated and non-annotated objects models. An EntityAccessStrategy is used to control how objects are read from or written to. The strategy is using the following convention:
<ol>
<li>Annotated method (getter/setter)</li>
<li>Annotated field</li>
<li>Plain method (getter/setter)</li>
<li>Plain field</li>
</ol>

#### POJOs
Nodes (and Relationships) are modeled as simple POJOs with a few annotations; 
<ul>
<li>Nodes are declared using the ```@org.neo4j.ogm.annotation.NodeEntity``` annotation.</li>
<li>Relationships are declared using the ```@org.neo4j.ogm.annotation.RelationshipEntity``` annotation.</li>
<li>Entities must have an empty public constructor to allow spring to construct the objects</li>
<li>Fields of an entity are by default mapped to properties of the node.</li>
<li>Fields referencing another node or relationship entity are linked with relationships.</li>
<li>All parent classes will be added as labels, retrieving nodes via parent type is possible. (in a case class A extends class B extends, labels of class A will only be A and C. To workaround that problem just add @NodeEntity to all classes in hierarchy)</li>
<li>Fields can be annotated with @Property, @GraphId, @Transient or @Relationship. @Transient fields won’t be persisted to the graph database.</li>
</ul>

Example
```
@NodeEntity
public abstract class Entity {

    //id is a necessary unique identifier for Neo4j. It will be generated automatically.
    @GraphId
    private Long id;

    private Long dbId;
    private String stId;
    private String name;

    @Relationship(type = "RELATIONSHIP_TYPE", direction = Relationship.OUTGOING)
    private Set<SOME_POJO> pojos;

    public Entity(Long dbId, String stId, String name) {
        this.dbId = dbId;
        this.stId = stId;
        this.name = name;
    }

    //Getters and Setters
    ...
```

#### Neo4j Template

SDN4 offers a Neo4jTemplate as an additional way of interacting with the Neo4j graph database. Neo4jTemplate is based on the ```org.neo4j.ogm.session.Session``` object and is essentially a wrapper around Session, exposing its methods and handles transactions.

#### Neo4j Session

The Session provides methods to load, save or delete objects. 

#### Repositories

SDN4 provides a repository infrastructure. Repositories consist of interfaces declaring functionality in each repository. Repositories provide commonly used persistence methods but are extensible by annotated methods, or derived finder methods. Repositories are based on the ```org.neo4j.ogm.session.Session``` object. 

Example
```
@Repository
public interface EntityRepository extends GraphRepository<Entity> {
//    GraphRepository cannot be of Type T

    @Query("MATCH (n:LABEL)<-[r:RELATIONSHIP_TYPE]-(y) " +
           "WHERE n.property={0} AND r.property={1} " +
           "RETURN n")
    Iterable<Entity> getSomeEntities1(String param1, Integer param2);

    @Query("MATCH (n:LABEL)<-[r:RELATIONSHIP_TYPE]-(y) " +
           "WHERE n.property={param1} AND r.proerty={param2}" +
           "RETURN n")
    Iterable<Entity> getSomeEntities2(@Param("param1") String param1, @Param("param2") Integer param2);

    //Derived finder methods
    Entity findByStId(String stId);
    Entity findByDbId(Long dbId);
    ...
```

#### Service

Service layer is as a wrapper around the repositories providing logic to operate on the data sent to and from the DAO. Transaction handling can be done in this layer using @Transactional.

Example
```
@Service
public final class EntityService {

    @Autowired
    private EntityRepository entityRepository;

    public Entity findByDbId(Long dbId) {
        return entityRepository.findByDbId(dbId);
    }

    //repositories already provide basic CRUD methods that don't need to be defined in the repositories.
    public Iterable<Entity> findAll() {
        return entityRepository.findAll();
    }
    ...
```

#### Setup Configuration and Bootstrapping 

Spring boot is used to bootstrap and launch a Spring application from a Java main method. Neo4j Configuration is done by overriding Neo4jServer and SessionFactory. As Beans they are accessible to spring. Connecting to Neo4j is handled by spring

Example
```
@Import(MyConfiguration.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
@EnableNeo4jRepositories
@EnableTransactionManagement
public class MyConfiguration extends Neo4jConfiguration {

    @Override
    @Bean
    public Neo4jServer neo4jServer() {
        System.setProperty("username", "neo4j");
        System.setProperty("password", "reactome");
        return new RemoteServer("http://localhost:7474");
    }

    @Override
    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory("uk.ac.ebi.reactome.domain");
    }

    @Override
    @Bean
    public Session getSession() throws Exception {
        return super.getSession();
    }

    @Override
    @Bean
    public Neo4jOperations neo4jTemplate() throws Exception {
        return new Neo4jTemplate(getSession());
    }
}
```

## Neo4j

#### Neo4j Browser

A Neo4j Browser panel can be accessed at localhost:7474/browser

#### Basic Cypher Commands

###### Reading

Show Neo4j Help
```
:help
```
Show Neo4j Schema (active Indexes or Constraints)
```
:schema
```
Show all labels
```
MATCH n RETURN DISTINCT Labels(n)
```
Show all relationship types
```
MATCH ()-[r]-() RETURN DISTINCT RelationshipTypes(r)
```
Match all nodes
```
MATCH n RETURN n
```
Match all nodes (limited amount)
```
MATCH (n) RETURN n LIMIT 25
```
Match all top lvl nodes
```
MATCH (n:Pathway) WHERE NOT (n)<-[*]-() RETURN n
```
Count all nodes
```
MATCH n RETURN COUNT(n)
```
Show all nodes and count their relationships
```
MATCH n-[r]-() RETURN n, COUNT(r) as rel_count ORDER BY rel_count DESC
```
Show all nodes without relationships
```
MATCH (n) WHERE NOT n--() RETURN n
```
Find Node by property
```
MATCH (n{dbId:someId}) RETURN n
```
Find Outgoing Subgraph
```
MATCH (n{dbId:264870})-[r*]->() RETURN r
```


FRONT PAGE ITEMS 
MATCH (n:Pathway) WHERE NOT (n)<-[]-() RETURN n

###### Writing

Create node with multiple labels and properties
```
CREATE (n:LABEL1:LABEL2:LABEL3{dbId:12345,stId:"R_HSA_12345"}) RETURN n
```
Merge node on dbId

```
MERGE (n:LABEL1:LABEL2:LABEL3 {dbId:12345})
ON CREATE SET n = {dbId:12345,stId:"R_HSA_12345"}
ON MATCH  SET n += {stId:"R_HSA_12345"}
RETURN n
```

Create relationship between two nodes using dbId
```
MATCH(a {dbId:12345}),(b {dbId:12346})
MERGE (a)<-[r:RELATIONSHIP_TYPE]-(b)
RETURN COUNT(r)=1")
```

Create relationship with property "cardinality" between two nodes using dbId
```
MATCH(a {dbId:{0}}),(b {dbId:{1}})
MERGE (a)<-[r:RELATIONSHIP_TYPE]-(b)
ON CREATE SET r =  {cardinality:1}
ON MATCH  SET r += {cardinality:(r.cardinality+1)}
RETURN COUNT(r)=1")
```

Delete all nodes and relationships
```
MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r
```

#### Neo4j Indexing

The MATCH will use an index if possible. If there is no index, it will lookup up all nodes carrying the label and see if the property matches. Indexes are always specified for specific labels.

Creating index (only one property can be specified per index)
```
CREATE INDEX ON :LABEL(property)
```

Dropping index
```
DROP INDEX ON :LABEL(property)
```

#### Neo4j Constraints

Data integrity can be enforced using constraints. Unique constraints can be created on nodes or relationships. Existence constraints are only available in Neo4j Enterprise Edition.
It is possible to have multiple constraints per label. Adding a unique property constraint will also add an index on that property. Constraints are label specific!

Creating constraint (only one property can be be set to unique for each constraint)
```
CREATE CONSTRAINT ON (n:LABEL) ASSERT n.dbId IS UNIQUE
```

Drop constraint
```
DROP CONSTRAINT ON (n:LABEL) ASSERT n.dbId IS UNIQUE
```






Match (n{dbId:109581})-[r]-() WHERE NOT (n)-[r:getModified]->() return n,r

match (n:Event)-[r]-(y:Species{dbId:48887}) return n 


MATCH (n:Pathway{dbId:75153})-[:hasEvent*]->(m:Pathway)  RETURN n,m 

MATCH (n:Event{dbId:373753})-[r:hasEvent|input|output|catalystActivity|hasMember|hasComponent|hasCandidate|repeatedUnit|referenceEntity*]->(m) WHERE m:ReferenceEntity RETURN DISTINCT m


MATCH (n:DatabaseObject{dbId:5673001})<-[r:hasEvent|input|output*]-(x)  RETURN n,x 


FIND TOP LEVEL PATHWAYS 



Neo4j REST INterface

firefox: chrome://resteasy/content/resteasy.html

POST: http://localhost:7474/db/data/transaction/commit
Header
Content-Type application/json
Authentication: neo4j reactome
Body
{
  "statements" : [ {
    "statement" : "MATCH (n:DatabaseObject{dbId:1500931})-[:hasEvent|input|output|positivelyRegulate|negativelyRegulate|entityFunctionalStatus|physicalEntity|catalystActivity|hasMember|hasComponent|hasCandidate|repeatedUnit|referenceEntity*]->(m) WHERE m:ReferenceEntity RETURN DISTINCT  m.dbId, m.identifier, m.displayName"
  } ]
}


Match (n:Summation)<-[r:summation]-() WITH n,count(r) as rel_cnt
WHERE rel_cnt > 1 Return n Limit 10

Loops
$Match (n)-[r]->(x) Where (x)-[r]->(n) Return n,r,x

Multiple rel for 2 nodes
Match (n)-[r]->(x) WITH n,x,COUNT(r) as cnt WHERE cnt>1 Return n,x,cnt Limit 10


"spring" 
Entities handled by Spring Data Neo4j must have an empty constructor to allow the library to construct
the objects.


Annotated and non-annoted objects can be used within the same project without issue. There is an
EntityAccessStrategy used to control how objects are read from or written to. The default
implementation of this uses the following convention:
1. Annotated method (getter/setter)
2. Annotated field
3. Plain method (getter/setter)
4. Plain field

Fields that are annotated as @Transient or declared with the transient modifier are exempted
from persistence. All fields that contain primitive values are persisted directly to the graph. All fields
convertible to a String using the Spring conversion services will be stored as a string. Spring Data
Neo4j includes default type converters that deal with the following types:
• java.util.Date to a String in the ISO 8601 format: "yyyy-MM-dd’T’HH:mm:ss.SSSXXX"
• java.math.BigInteger to a String property
• java.math.BigDecimal to a String property
• binary data (as byte[] or Byte[]) to base-64 String
• java.lang.Enum types using the enum’s name() method and Enum.valueOf()





/**
 * how to generally manage a doubly-linked list using SDN 4, specifically for the case where the underlying graph uses a single relationship type between nodes, e.g.

 (post:Post)-[:NEXT]->(post:Post)

 What you can't do

 Due to limitations in the mapping framework, it is not possible to reliably declare the same relationship type twice in two different directions in your object model, i.e. this (currently) will not work:

 class Post {
@Relationship(type="NEXT", direction=Relationship.OUTGOING)
Post next;

@Relationship(type="NEXT", direction=Relationship.INCOMING)
Post previous;
}

 What you can do

 Instead we can combine the @Transient annotation with the use of annotated setter methods to obtain the desired result:

 class Post {
 Post next;

 @Transient Post previous;

 @Relationship(type="NEXT", direction=Relationship.OUTGOING)
 public void setNext(Post next) {
 this.next = next;
 if (next != null) {
 next.previous = this;
 }
 }
 }

 As a final point, if you then wanted to be able to navigate forwards and backwards through the entire list of Posts from any starting Post without having to continually refetch them from the database, you can set the fetch depth to -1 when you load the post, e.g:

 findOne(post.getId(), -1);

 Bear in mind that an infinite depth query will fetch every reachable object in the graph from the matched one, so use it with care!
 */




 A query such as

 MATCH (user:User {name: {username})-[rating:RATED]->(movie:Movie) RETURN u,r,m


 executed by Session.query() will continue to return a Result which contains keys for user, rating and movie but the values are now domain node and relationship entities linked by relationships returned in the query.

 This will make it much easier to deal with the results of custom queries and improve performance since your code will no longer need an additional load of the entity by ID.
  */


  Find Pathways till root
  Match (n:DatabaseObject{dbId:70523})<-[:hasEvent*]-(x:Pathway) Return n,x;

  Match (n:DatabaseObject{dbId:163042})<-[r:hasEvent|input|output|hasComponent*]-(x:DatabaseObject) Return n,x,r;

  Identify all selfloop relationships
  Match (n)-[r]-(x) WHERE n=x RETURN n;

  Identify all cyclic between 2 nodes
  Match (n)-[r:reverseReaction|inferredTo|hasEvent]->(x),(n)<-[e:reverseReaction|inferredTo|hasEvent]-(x) RETURN n,e,r,x LIMIT 25



Orphan guy example
R-MMU-210774

Match (n)-[r]-(x),(n)-[e]-(x) WHERE NOT (n)-[:author|created|edited|modified|revised|reviewed|input|output]-(x) RETURN Count(n)

maven deploy -> deploy nexus
maven site:site
maven site:deploy


Pathways without hasEvents
Match (n:Pathway)<-[:created]-(a) Where NOT (n)-[:hasEvent]->() RETURN n.dbId AS DbID, a.displayName as Author
EWAS without
Match (n:EntityWithAccessionedSequence)<-[:created]-(a) Where NOT (n)-[:referenceEntity]->() RETURN n.dbId AS DbID, a.displayName as Author
Events and PHysicalEntities without stid
Match (n)<-[:created]-(a) Where (n:Event OR n:PhysicalEntity) AND n.stableIdentifier is NULL RETURN n.dbId AS dbId,  a.displayName as author



Match (n:Reaction)<-[:created]-(a) Where NOT (n)-[:output|input]->() RETURN n.dbId AS dbId, n.stableIdentifier AS stId, n.displayName AS name, a.displayName as author

Match (n:Component)<-[:created]-(a) Where NOT (n)-[:hasComponent]->() RETURN n.dbId AS dbId, n.stableIdentifier AS stId, n.displayName AS name, a.displayName as author

Match (n:EntitySet)<-[:created]-(a) Where NOT (n)-[:hasMember|hasCandidate]->() RETURN n.dbId AS dbId, n.stableIdentifier AS stId, n.displayName AS name, a.displayName as author


simple entity does not need to have reference Entity ?
Match (n:SimpleEntity)<-[:created]-(a) Where NOT (n)-[:referenceEntity]->() RETURN n.dbId AS dbId, n.stableIdentifier AS stId, n.displayName AS name, a.displayName as author

reference identifier is null
Match (n:ReferenceEntity)<-[:created]-(a) Where n.identifier is NULL RETURN n.dbId AS dbId, n.stableIdentifier AS stId, n.displayName AS name, a.displayName as author

Instance Edit
Match (n:InstanceEdit)<-[:created]-(a) Where NOT (n)<-[:author]-() RETURN n.dbId AS dbId

DatabaseObject no name:
idnk



LAST modified
Match (n:Pathway)-[:precedingEvent]->(),(n)-[:species]->(s), (n)<-[:modified]-(m) OPTIONAL MATCH (n)-[:created]-(a) WITH s,a,n,max(m.dateTime) as max MATCH (n)<-[:modified]-(m) WHERE m.dateTime = max AND s.displayName="Homo sapiens" RETURN DISTINCT(n.dbId) AS dbId, n.stableIdentifier AS stId, n.displayName AS name, a.displayName AS createdBy, m.displayName AS lastModified