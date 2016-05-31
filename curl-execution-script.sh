#!/bin/bash

NeoTotal=0
GkTotal=0
k=1
while read line
do

    START=$(($(date +%s%N)/1000000))
    echo ${line}
#    curl   -H "Content-Type: application/json" --user neo4j:reactome --data "{\"statements\" : [ { \"statement\" : \"MATCH (n:DatabaseObject{stableIdentifier:'"${line}"'})-[r]-(m) RETURN n,r,m\"}]}" http://localhost:7474/db/data/transaction/commit
    curl --write-out "%{http_code}\n" --silent --output /dev/null  "http://localhost:8585/data/detail/"${line}

    END=$(($(date +%s%N)/1000000))
    DIFF=$((END-START))
    echo "Neo4j execution time: "${DIFF}
    echo ${DIFF} >> time.txt
    NeoTotal=$((NeoTotal+DIFF))

#    START=$(($(date +%s%N)/1000000))
#    curl --write-out "%{http_code}\n" --silent --output /dev/null "http://www.reactome.org/ReactomeRESTfulAPI/RESTfulWS/queryById/Pathway/"${line}
#    END=$(($(date +%s%N)/1000000))
#    DIFF=$((END-START))
#    echo "GK execution time: "$DIFF
#    GkTotal=$((GkTotal+DIFF))
    ((k++))

done < /home/flo/pathwayIds.txt

echo "Neo4j avg execution time: "
echo $((NeoTotal/k))
echo "GK avg execution time: "
echo $((GkTotal/k))


#    START=$(($(date +%s%N)/1000000))
#    curl  --write-out "%{http_code}\n" --silent --output /dev/null  -H "Content-Type: application/json" --user neo4j:reactome --data "{\"statements\" : [ { \"statement\" : \"MATCH (n:DatabaseObject{stId:'"${line}"'})-[r]-(m) RETURN n,r,m\"}]}" http://localhost:7474/db/data/transaction/commit
#
#    END=$(($(date +%s%N)/1000000))
#    DIFF=$((END-START))
#    echo "Neo4j execution time: "$DIFF
#    NeoTotal=$((NeoTotal+DIFF))


##!/bin/bash
#
#NeoTotal=0
#k=1
#while read line
#do
#
#    START=$(($(date +%s%N)/1000000))
#    curl  "http://localhost:8585/data/detail/"${line} >/dev/null 2>&1
#    END=$(($(date +%s%N)/1000000))
#    DIFF=$((END-START))
#    NeoTotal=$((NeoTotal+DIFF))
#
#    START=$(($(date +%s%N)/1000000))
#    curl  "http://www.reactome.org/ReactomeRESTfulAPI/RESTfulWS/queryById/Pathway/"${line} >/dev/null 2>&1
#    END=$(($(date +%s%N)/1000000))
#    DIFF=$((END-START))
#
#    ((k++))
#
#done < /home/flo/pathwayIds.txt
#
#echo $((NeoTotal/k))
#echo "Total number of lines in file: $k"
