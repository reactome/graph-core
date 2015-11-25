package uk.ac.ebi.reactome.data;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.ebi.reactome.domain.model.Interactor;
import uk.ac.ebi.reactome.service.InteractorService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by flo on 28.10.15.
 */
@Component
public class IntactParser {

    private static final String INTACT_FILE_URL = "ftp://ftp.ebi.ac.uk/pub/databases/intact/current/psimitab/intact-micluster.txt";

    @Autowired
    private InteractorService interactorService;


    // do try with resources here
    public void parseIntactfile() {

//        The intended use is for initial import of data but you can use it on an existing database if the existing database is shutdown first.
//        Batch insertion is not thread safe.
//        Batch insertion is non-transactional.
//        Warning
//        Always perform batch insertion in a single thread (or use synchronization to make only one thread at a time access the batch inserter) and invoke shutdown when finished.
//        Warning
//        Since the batch insertion doesn’t enforce constraint during data loading, if the inserted data violate any constraint the batch inserter will fail on shutdown and the database will be inconsistent.

        BatchInserter inserter = null;
        try
        {

            inserter = BatchInserters.inserter(new File("/var/lib/neo4j/data/graph.db").getAbsolutePath());

            Label personLabel = DynamicLabel.label("Interactor");
            inserter.createDeferredConstraint( personLabel ).assertPropertyIsUnique("identifier");
            inserter.createDeferredSchemaIndex( personLabel ).on("identifier" ).create();

            File file = new File("intact-micluster.txt");

            try {
                LineIterator it = FileUtils.lineIterator(file , "UTF-8");
                it.nextLine();
                Map<String, Long> idmap = new HashMap<>();

//                MAtch (n:ReferenceEntity) RETURN n.identifier
//                map for finding connections to reactome ... just 1 query to graph db needed

                int i = 0;
                while (it.hasNext()) {
                    i++;
                    String line = it.nextLine();
//                    System.out.println(line);// do something with line
                    String[] array = line.split("\\t");
                    long a;
                    if(idmap.containsKey(array[0])){
                        a=idmap.get(array[0]);
                    } else {
                        Map<String, Object> properties = new HashMap<>();
                        properties.put( "identifier", array[0] );
                        a = inserter.createNode(properties, personLabel);
                        properties.put( "identifier", array[1] );
                        idmap.put(array[0],a);
                    }
                    long b;
                    if(idmap.containsKey(array[1])){
                        b=idmap.get(array[1]);
                    } else {
                        Map<String, Object> properties = new HashMap<>();
                        properties.put( "identifier", array[0] );
                        b = inserter.createNode(properties, personLabel);
                        properties.put( "identifier", array[1] );
                        idmap.put(array[1],b);
                    }
                    RelationshipType knows = DynamicRelationshipType.withName("INTERACT");

                    inserter.createRelationship( a,  b, knows, null );

                }
                System.out.println(i);
                LineIterator.closeQuietly(it);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            inserter.
//
//            Map<String, Object> properties = new HashMap<>();
//
//            properties.put( "name", "Mattias" );
//            long mattiasNode = inserter.createNode( properties, personLabel );
//
//            properties.put( "name", "Chris" );
//            long chrisNode = inserter.createNode( properties, personLabel );
//
//            RelationshipType knows = DynamicRelationshipType.withName("KNOWS");
//            inserter.createRelationship( mattiasNode, chrisNode, knows, null );
        }
        finally
        {
            if ( inserter != null )
            {
                inserter.shutdown();
            }
        }
//        File file = new File("intact-micluster.txt");
//
//        try {
//            LineIterator it = FileUtils.lineIterator(file , "UTF-8");
//            it.nextLine();
//            while (it.hasNext()) {
//                String line = it.nextLine();
//                System.out.println(line);// do something with line
//                String[] array = line.split("\\t");
//                addLineToDatabase(array);
//            }
//            LineIterator.closeQuietly(it);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        try {
//            BufferedReader in = new BufferedReader(new InputStreamReader(new URL(INTACT_FILE_URL).openStream()));
//            String line = in.readLine();
//            while ((line = in.readLine()) != null) {
//                String[] array = line.split("\\t");
//                addLineToDatabase(array);
//            }
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private  void addLineToDatabase (String[] array) {

        interactorService.merge(new Interactor(array[0], array[4]));
        interactorService.merge(new Interactor(array[1], array[5]));
//        author score: sometimes low high ...., sometimes numbers
//        author score:"Number of different experiments interaction was observed in: 1"|author score:0.263|intact-miscore:0.8316398
        if (!array[14].isEmpty()) {
            String[] scores = array[14].split("\\|");
            if (!scores[scores.length - 1].isEmpty()) {
                Double score = Double.parseDouble(scores[scores.length - 1].replaceAll("[^0-9.]", ""));
                interactorService.createInteraction(array[0], array[1], score);
            }
        }
    }
}
