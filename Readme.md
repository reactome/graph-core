![Logo](https://cdn.evbuc.com/images/3621635/40070539972/1/logo.png)

# Reactome Graph Database

## What is the Reactome Graph project
 
The Reactome Graph Project aims to model the [Reactome knowledgebase](http://www.reactome.org) into a interconnected graph.

At the cellular level, life is a network of molecular reactions. In Reactome, these processes are systematically described in molecular detail to generate an ordered network of molecular transformations (Fabregat et al. 2015). This amounts to millions of interconnected terms naturally forming a graph of biological knowledge. The Reactome Graph aims to provide an intuitive way for data retrieval as well as interpretation and analysis of pathway knowledge. 

Retrieving, and especially analyzing such complex data becomes tedious when using relational Databases. Queries across the pathway knowledgebase are composed by a number of expensive join operations resulting in bad performance and a hard-to-maintain project. Due to the schema based approach, relational databases are limited on how information will be stored and thus are difficult to be scaled for new requirements. 
In order to overcome these problems the Reactome database is imported in [Neo4j](http://neo4j.com), creating one big interconnected graph. The Graph database technology is an effective tool for modelling highly connected data. Since molecular networks are by their nature represented as a graph, storing Reactome data is in many ways beneficial: Firstly no normalization is required, data can be stored in its natural form. Secondly, nodes in the vicinity of a starting point can quickly be traversed, giving the user the possibility to not only retrieve data but perform fast analysis of these neighbor networks. Thus, knowledge can be retrieved that previously was not available due to the limitations of the relational data storage.

In the Reactome Graph project Neo4j, a Java implemented, open source, transactional database, with native graph storage and processing, is utilized. Neo4j offers multiple integration possibilities for Java development. In this project, [String Data Neo4j](http://projects.spring.io/spring-data-neo4j/)    provides automatic object graph mapping on top of Neo4j and integrates tightly with other parts of the spring framework used across the project.

#### Reactome graph limitations:

While graph databases perform great on graph-local questions, there are possible drawbacks of the use of Neo4j in Reactome. Set oriented queries with the simple goal of aggregating large lists of properties, that does not require a lot of joins in the relational database will perform not be as favorable. Similarly global operations, operations on the entire data set can possibly be outperformed by the RDBMS.

#### Project components used:

* Neo4j - version: 2.3.0
* Spring Data Neo4j - version: 4.1.0

#### Project usage: 

The project can be used as a "Core" library providing a Data Object Layer on top of the Neo4j Db. Additional there are two entry points have been defined:

* Import-Module   Used for initially importing Reactome data to the graph
* QA-Module       Quality Assertion module: Used for checking the consistency of the Reactome data. 

**CAUTION!**

1. Using the QA-Module or the library requires a running Neo4j standalone server on port: 7474
2. When executing the Import-Module Neo4j should not be running, and rights to the folder of the graph.db should be granted.
3. When using the graph-core dependency neo4j credentials must be provided as System.properties with keys:
    * "neo4j.user"
    * "neo4j.password"

**Dependency** 

```
<dependency>
    <groupId>org.reactome.server.tools</groupId>
    <artifactId>graph-core</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

**Repository**

```
<repository>
    <id>reactome-nexus</id>
    <name>The Reactome local repository</name>
    <url>http://localhost:8081/nexus/content/repositories/snapshots//</url>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>
```

#### Maven test

Maven tests will only be executed if a connection to Neo4j can be established. Otherwise all tests will be skipped. All parameters needed to execute the JunitTests have to be provided as maven profile:

```
<profile>
    <id>neo4j</id>
    <properties>
        <neo4j.user>neo4j</neo4j.user>
        <neo4j.password>reactome</neo4j.password>
        <reactome.host>localhost</reactome.host>
        <reactome.port>3306</reactome.port>
        <reactome.database>reactome</reactome.database>
        <reactome.user>reactome</reactome.user>
        <reactome.password>reactome</reactome.password>
    </properties>
</profile>
```

#### Project deployment

Using maven this project can be deployed to a nexus repository.

###### Repo deployment

For deploying the project use: ```mvn deploy```. Projects with suffix _-SNAPSHOT_ will automatically be deployed to the nexus snapshot repository, without any suffix projects will deploy to release. Repositories can be found:
* http://localhost:8081/nexus/content/repositories/releases/
* http://localhost:8081/nexus/content/repositories/snapshots/

Authentication for accessing nexus needs to be specified in the maven settings.xml:

```
<servers>
    <server>
        <id>nexus-release</id>
        <username>user</username>
        <password>password</password>
    </server>
    <server>
        <id>nexus-snapshot</id>
        <username>user</username>
        <password>password</password>
    </server>
</servers>
```

###### Site deployment

Maven _site_ provides a project summary and JavaDoc documentation. To create the maven _site_ htmls use: ```mvn site:site```. To deploy maven _site_ use: ```mvn site:deploy```. Note that deployment requires a successful execution of _site_.  When deployed it will be hosted by nexus: http://localhost:8081/nexus/content/sites/site/.
Authentication for accessing nexus needs to be specified in the maven settings.xml:

```
<servers>
    <server>
        <id>nexus-site</id>
        <username>user</username>
        <password>password</password>
    </server>
</servers>
```

#### Neo4j setup

```setup-graph.sh``` is a script for Neo4j installation/upgrading, initial data import and deployment of the Reactome Graph project to nexus. For additional information and usage: ```setup-graph.sh -h``` 

#### Installing Neo4j without the script

Neo4j can be installed in linux with the command line

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

Neo4j can be controlled in the command line

```
sudo service neo4j-service start
sudo service neo4j-service stop
sudo service neo4j-service status
sudo service neo4j-service restart
sudo service neo4j-service info
```

**CAUTION!**
http://neo4j.com/docs/stable/server-installation.html
This approach to running Neo4j as a server is deprecated. We strongly advise you to run Neo4j from a package where feasible.
You can build your own init.d script. See for instance the Linux Standard Base specification on system initialization, or one of the many samples and tutorials.
http://refspecs.linuxfoundation.org/LSB_3.1.0/LSB-Core-generic/LSB-Core-generic/tocsysinit.html
http://www.linux.com/learn/tutorials/442412-managing-linux-daemons-with-init-scripts

**Fix Neo4j MaxOpenFilesWarning**

* To show current MaxOpenFiles limit ```ulimit -n```
* To edit the MaxOpenFiles number goto ```/etc/security/limits.conf``` and edit:

```
root soft nofile 40000
root hard nofile 40000
neo4j soft nofile 40000
neo4j hard nofile 40000
user soft nofile 40000
user hard nofile 40000
```

* To enforce the specified limits goto ```/etc/pam.d/su``` and edit:
```
session required pam_limits.so
```

#### Neo4j Authentication

* Neo4j Server is bundled with a Web server answering only requests from the local machine. This behavior can be changed in ```/conf/neo4j-server.properties```.
* Neo4j requires clients to supply authentication credentials. The authentication data is stored under ```/var/lib/neo4j/data/dbms/auth```.
* Authentication can also be changed in the Neo4j-Browser with the command: ```:server change-password```

#### Neo4j Remote Access

To allow remote access to neo4j enable ```org.neo4j.server.webserver.address=0.0.0.0``` and restart the server. This behavior can be changed in ```/conf/neo4j-server.properties```.

#### Reactome data import

Reactome data will be automatically be imported when running the ```setup-graph.sh``` script. 

#### Data Import without the script

Reactome data can be imported without the script using the DataImportLauncher entry point. Use: ```java-jar DataImport.jar```
**CAUTION!**
In order for the import to succeed following steps must be ensured:
 1) Neo4j database can not be running.
 2) All rights of the neo4j database folder (graph.db) must be granted to the executing user of the jar file.
 3) Be sure to reverse these steps after successful data import.

#### Quality Assurance

The quality assurance module will be automatically run during the ```setup-graph.sh``` script. 

#### Quality Assurance without the script

The quality assurance module can be executed without the script using the QualityAssurance endpoint. This endpoint requires a running instance of neo4j. Use: ```java-jar QualityAssurance.jar```

#### Project Structure

The application follows a basic spring multi-tier architecture:

* Presentation Layer - Outside of the scope of this project. The projects acts as a data access layer for the content service. To see details about the content service goto ...
* Service Layer - Service and Spring Transactions.
* Persistence Layer - Provided by SpringDataNeo4j repositories and neo4jTemplate
* Database - Neo4j Standalone server

#### SpringDataNeo4j-4 (SND4)

SDN4 (version 4.0.0) h .... Cypher and HTTP to communicate with the database.

Cypher statements are executed against Neo4j Server using an HTTP-based protocol, which is utilised 
by Spring Data Neo4j....

SDN4 provides among other things:

* Object-Graph-Mapping of annotated POJO entities
* Repository infrastructure. Repositories provide commonly used persistence methods and can be extended by annotated, named or derived "finder" methods

#### Object Graph Mapping 

When an entity is constructed from a node or relationship the framework will attempt to map fields of an object to node properties or related nodes. SDN4 supports mapping annotated and non-annotated objects models. An EntityAccessStrategy is used to control how objects are read from or written to. The strategy is using the following convention:

* Annotated method (getter/setter)
* Annotated field
* Plain method (getter/setter)
* Plain field

#### POJOs

Nodes (and Relationships) are modeled as simple POJOs with a few annotations; 

* Nodes are declared using the ```@org.neo4j.ogm.annotation.NodeEntity``` annotation.
* Relationships are declared using the ```@org.neo4j.ogm.annotation.RelationshipEntity``` annotation.
* Fields can be annotated with:  
    * @Property - simple attribute
    * @GraphId - unique identifier, will be automatically set
    * @Transient - fields that won't be persisted
    * @Relationship - referencing other nodes or relationship entries

Other requirements:

* Entities must have an empty public constructor to allow spring to construct the objects.
* Fields of an entity are by default mapped to properties of the node.
* Fields referencing another node or relationship entity are linked with relationships.
* All parent classes will be added as labels, retrieving nodes via parent type is possible. 

#### Neo4j Template

SDN4 offers a Neo4jTemplate as an additional way of interacting with the Neo4j graph database. Neo4jTemplate is based on the ```org.neo4j.ogm.session.Session``` object and is essentially a wrapper around Session, exposing its methods and handles transactions.

#### Neo4j Session

The Session provides methods to load, save or delete objects. 

#### Repositories

SDN4 provides a repository infrastructure. Repositories consist of interfaces declaring functionality in each repository. Repositories provide commonly used persistence methods but are extensible by annotated methods, or derived finder methods. Repositories are based on the ```org.neo4j.ogm.session.Session``` object. 

#### Service

Service layer is as a wrapper around the repositories providing logic to operate on the data sent to and from the DAO. Transaction handling can be done in this layer using @Transactional.

## Neo4j Administration

#### Neo4j Browser

A Neo4j Browser panel can be accessed at ```localhost:7474/browser```

#### Cypher

Neo4j provides a graph query language: [**Cypher**](http://neo4j.com/docs/stable/cypher-query-lang.html)
 
#### Neo4j Browser meta commands

Command | Description
--- | --- 
:help | show neo4j help
:schema | show neo4j schema (all active Indexes or Constraints)
:history | shows history of commands
:server | shows options for managing Neo4j connection

#### Basic Cypher Commands

###### Reading

Command | Return
--- | --- 
```MATCH n RETURN DISTINCT Labels(n)``` | Show all labels
```MATCH ()-[r]-() RETURN DISTINCT RelationshipTypes(r)``` | Show all relationship types
```MATCH (n) RETURN n LIMIT 25``` | Match all nodes (limited amount)
```MATCH n RETURN COUNT(n)``` | Count all nodes
```MATCH n-[r]-() RETURN n, COUNT(r) as rel_count ORDER BY rel_count DESC``` | Show all nodes and count their relationships
```MATCH (n{dbId:someId}) RETURN n``` | Find Node by property
```MATCH (n{dbId:264870})-[r*]->() RETURN r``` | Find Outgoing sub-graph

###### Writing

* Create node with multiple labels and properties
```
CREATE (n:LABEL1:LABEL2:LABEL3{dbId:12345,stId:"R_HSA_12345"}) RETURN n
```
* Merge node on dbId
```
MERGE (n:LABEL1:LABEL2:LABEL3 {dbId:12345})
ON CREATE SET n = {dbId:12345,stId:"R_HSA_12345"}
ON MATCH  SET n += {stId:"R_HSA_12345"}
RETURN n
```
* Create relationship between two nodes using dbId
```
MATCH(a {dbId:12345}),(b {dbId:12346})
MERGE (a)<-[r:RELATIONSHIP_TYPE]-(b)
RETURN COUNT(r)=1")
```
* Create relationship with property "cardinality" between two nodes using dbId
```
MATCH(a {dbId:{0}}),(b {dbId:{1}})
MERGE (a)<-[r:RELATIONSHIP_TYPE]-(b)
ON CREATE SET r =  {cardinality:1}
ON MATCH  SET r += {cardinality:(r.cardinality+1)}
RETURN COUNT(r)=1")
```
* Delete all nodes and relationships
```
MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r
```

#### Neo4j Indexing

The MATCH will use an index if possible. If there is no index, it will lookup up all nodes carrying the label and see if the property matches. Indexes are always specified for specific labels.

Command | Description 
--- | --- 
```CREATE INDEX ON :LABEL(property)``` | Creating index (only one property can be specified per index)
```DROP INDEX ON :LABEL(property)``` | Dropping index


#### Neo4j Constraints

Data integrity can be enforced using constraints. Unique constraints can be created on nodes or relationships. Existence constraints are only available in Neo4j Enterprise Edition. It is possible to have multiple constraints per label. Adding a unique property constraint will also add an index on that property. Constraints are label specific!

Command | Description 
--- | --- 
```CREATE CONSTRAINT ON (n:LABEL) ASSERT n.dbId IS UNIQUE``` | Creating constraint (only one property can be be set to unique for each constraint)
```DROP CONSTRAINT ON (n:LABEL) ASSERT n.dbId IS UNIQUE``` | Drop constraint


## Neo4j RESTful Endpoint

The standalone Neo4j Server can accessed directly using the [Neo4j-RESTful API](http://neo4j.com/docs/stable/rest-api.html). The Neo4j HTTP endpoint allows the execution of a series of Cypher statements. 

**Header**

Command | Description
--- | --- 
POST | http://localhost:7474/db/data/transaction/commit
Accept | application/json; charset=UTF-8
Content-Type | application/json

**Body simple query:**

```
     Content-Type: application/json 

{
  "statements" : [ {
    "statement" : "CREATE (n) RETURN id(n)"
  } ]
}
```

**Body simple query with parameters:**

```
{
  "statements" : [ {
    "statement" : "CREATE (n) RETURN id(n)"
  }, {
    "statement" : "CREATE (n {props}) RETURN n",
    "parameters" : {
      "props" : {
        "name" : "My Node"
      }
    }
  } ]
}
```