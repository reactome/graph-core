package uk.ac.ebi.reactome;

import com.martiansoftware.jsap.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.reactome.data.ReactomeBatchImporter2;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 13.11.15.
 */
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * The main() method uses Spring Boot’s SpringApplication.run() method to launch an application.
     * @param args Neo4j username and password can be passed on the command line:
     *             mvn spring-boot:run -Drun.jvmArguments="-Dusername=user -Dpassword=password"
     */
    public static void main(String[] args) throws JSAPException {


        SimpleJSAP jsap = new SimpleJSAP(Application.class.getName(), "A tool for importing reactome data to the neo4j graphDb",
                new Parameter[]{
                        new FlaggedOption("host",     JSAP.STRING_PARSER, "localhost",  JSAP.NOT_REQUIRED, 'h', "host",     "The database host"),
                        new FlaggedOption("port",     JSAP.INTEGER_PARSER,"3306",       JSAP.NOT_REQUIRED, 's', "port",     "The reactome port"),
                        new FlaggedOption("database", JSAP.STRING_PARSER, "Reactome",   JSAP.NOT_REQUIRED, 'd', "database", "The reactome database name to connect to"),
                        new FlaggedOption("user",     JSAP.STRING_PARSER, "root",       JSAP.NOT_REQUIRED, 'u', "user",     "The database user"),
                        new FlaggedOption("password", JSAP.STRING_PARSER, "reactome",   JSAP.NOT_REQUIRED, 'p', "password", "The password to connect to the database")
                }
        );

        JSAPResult config = jsap.parse(args);
        if (jsap.messagePrinted()) System.exit(1);

        /**
         * @Autowired annotation does not work in a static context. context.getBean has to be used instead.
         * final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfiguration.class);
         * ReactomeBatchImporter2 batchImporter = ctx.getBean(ReactomeBatchImporter2.class);
         */
        ReactomeBatchImporter2 batchImporter = new ReactomeBatchImporter2(
                config.getString("host"),
                config.getString("database"),
                config.getString("user"),
                config.getString("password"),
                config.getInt("port"));
        batchImporter.importAll();
    }
}