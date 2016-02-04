package uk.ac.ebi.reactome;

import com.martiansoftware.jsap.*;
import uk.ac.ebi.reactome.data.ReactomeBatchImporter;

import java.io.IOException;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 13.11.15.
 */
public class Application {

    public static void main(String[] args) throws JSAPException, IOException {


        SimpleJSAP jsap = new SimpleJSAP(Application.class.getName(), "A tool for importing reactome data to the neo4j graphDb",
                new Parameter[]{
                        new FlaggedOption("host",     JSAP.STRING_PARSER, "localhost",  JSAP.NOT_REQUIRED, 'h', "host",     "The database host"),
                        new FlaggedOption("port",     JSAP.INTEGER_PARSER,"3306",       JSAP.NOT_REQUIRED, 's', "port",     "The reactome port"),
                        new FlaggedOption("database", JSAP.STRING_PARSER, "Reactome",   JSAP.NOT_REQUIRED, 'd', "database", "The reactome database name to connect to"),
                        new FlaggedOption("user",     JSAP.STRING_PARSER, "root",       JSAP.NOT_REQUIRED, 'u', "user",     "The database user"),
                        new FlaggedOption("password", JSAP.STRING_PARSER, "reactome",   JSAP.NOT_REQUIRED, 'p', "password", "The password to connect to the database"),
                        new FlaggedOption("dir",      JSAP.STRING_PARSER, "/var/lib/neo4j/data/graph.db",   JSAP.NOT_REQUIRED, 'l', "dir", "The path and dir to the database")
                }
        );

        JSAPResult config = jsap.parse(args);
        if (jsap.messagePrinted()) System.exit(1);

        /**
         * @Autowired annotation does not work in a static context. context.getBean has to be used instead.
         * final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfiguration.class);
         * ReactomeBatchImporter batchImporter = ctx.getBean(ReactomeBatchImporter.class);
         */
        ReactomeBatchImporter batchImporter = new ReactomeBatchImporter(
                config.getString("host"),
                config.getString("database"),
                config.getString("user"),
                config.getString("password"),
                config.getInt("port"),
                config.getString("dir"));
        batchImporter.importAll();
    }
}