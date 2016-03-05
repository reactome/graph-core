#!/bin/bash

#-----------------------------------------------------------
# Script for an initial data import of Reactome to the Graph  
# Execute as $sudo ./setup-graph.sh -h
#
#
# 4 March 2015
# Florian Korninger - fkorn@ebi.ac.uk
#  
#-----------------------------------------------------------

usage="
This is a script for import and setup of the Reactome graph Db. 

$(basename "$0") [-r <reactome_host> -s <reactome_port> —t <reactome_db_name> -u <reactome_db_user> -v <reactome_db_password> -d <neo4j_db_directory> -e <neo4j_db_name> -i <install_neo4j> -m <neo4j_user> -n <neo4j_password> ] 

where:
    -h  Program help/usage
    -r  Reactome database host. DEFAULT: localhost
    -s  Reactome database port. DEFAULT: 3306
    -t  Reactome database name. DEFAULT: reactome
    -u  Reactome database user. DEFAULT: reactome
    -v  Reactome database password. DEFAULT: reactome
    -d  Neo4j directory of Db. DEFAULT: /var/lib/neo4j/data/
    -e  Neo4j name of graph Db. DEFAULT: graph.db
    -i  Install neo4j. DEFAULT: flase
    -m  Neo4j user (only set when neo4j is installed). DEFAULT: reactome
    -n  Neo4j password (only set when neo4j is installed). DEFAULT: reactome"	

_REACTOME_HOST="localhost"
_REACTOME_PORT=3306
_REACTOME_DATABASE="reactome"
_REACTOME_USER="reactome"
_REACTOME_PASSWORD="reactome"
_GRAPH_DIR="/var/lib/neo4j/data/"
_GRAPH_NAME="graph.db"
_INSTALL_NEO4J=false
_NEO4J_USER="reactome"
_NEO4J_PASSWORD="reactome"

# :h (help) should be at the very end of the while loop
while getopts ‘:r:s:t:u:v:d:e:m:n:ih’ option; do
  case "$option" in
    h) echo "$usage"
       exit
       ;;
    r) _REACTOME_HOST=$OPTARG
       ;;
    s) _REACTOME_PORT=$OPTARG
       ;;
    t) _REACTOME_DATABASE=$OPTARG
       ;;
    u) _REACTOME_USER=$OPTARG
       ;;
    v) _REACTOME_PASSWORD=$OPTARG
       ;; 
    d) _GRAPH_DIR=$OPTARG
       ;;
    e) _GRAPH_NAME=$OPTARG
       ;;
    i) _INSTALL_NEO4J=true
       ;;
    m) _NEO4J_USER=$OPTARG
       ;;
    n) _NEO4J_PASSWORD=$OPTARG
       ;;
   \?) echo "Invalid option: -$OPTARG" >&2
       echo "$usage" >&2
       exit 1
       ;;
  esac
done
shift $((OPTIND - 1))


echo $_REACTOME_HOST
echo $_REACTOME_PORT
echo $_REACTOME_DATABASE
echo $_REACTOME_USER
echo $_REACTOME_PASSWORD
echo $_GRAPH_DIR
echo $_GRAPH_NAME
echo $_INSTALL_NEO4J
echo $_NEO4J_USER
echo $_NEO4J_PASSWORD

if $_INSTALL_NEO4J = true; then 
    echo "start installing neo4j"
    sudo wget -O - https://debian.neo4j.org/neotechnology.gpg.key| apt-key add -
    echo 'deb http://debian.neo4j.org/repo stable/' > /etc/apt/sources.list.d/neo4j.list
    sudo aptitude update -q
    sudo aptitude install neo4j
    echo "installing neo4j finished"
 #update user password ehre 

fi

if sudo service neo4j-service status; then
    echo "Shutting down Neo4j DB"
    if ! sudo service neo4j-service stop; then 
        echo "an error occurred while trying to shut down neo4j db"
	exit 1
    fi
fi

git clone https://fkorn@bitbucket.org/fabregatantonio/graph-reactome.git
git -C ./graph-reactome/ fetch && git -C ./graph-reactome/  checkout master
#if ! git clone https://fkorn@bitbucket.org/fabregatantonio/graph-reactome.git; then 
#    echo "an error occured while cloning git repository"
#    exit 1
#fi

if ! mvn -q -f ./graph-reactome/pom.xml clean package -DskipTests; then 
    echo "an error occured while packaging the project"
    exit 1
fi 

sudo rm -R graph-reactome

echo "blabl" 

exit 


echo "Deleting old solr installed instances..."

echo "Stopping solr service"
service neo4j-service stop
chown -R ... $_GRAPH_PATH.$_GRAPH_NAME 

check current wd if stuff is there 
git clone ... repo 


java -jar .....jar

chown -R ... $_GRAPH_PATH.$_GRAPH_NAME 
service neo4j-service start

mvn test
java-jar QA.jar

echo "Done!"
