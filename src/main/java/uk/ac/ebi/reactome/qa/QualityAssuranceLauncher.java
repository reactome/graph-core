package uk.ac.ebi.reactome.qa;

import com.martiansoftware.jsap.*;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import uk.ac.ebi.reactome.config.MyConfiguration;
import uk.ac.ebi.reactome.qa.tests.QualityAssuranceAbstract;
import uk.ac.ebi.reactome.service.GenericService;

import java.util.Set;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 04.03.16.
 */
@Configuration
public class QualityAssuranceLauncher {

    public static void main(String[] args) throws JSAPException {

        SimpleJSAP jsap = new SimpleJSAP(QualityAssuranceLauncher.class.getName(), "A tool for testing the integrity and consistency of the dataimport of an existing graphDb",
                new Parameter[]{
                        new FlaggedOption("user",       JSAP.STRING_PARSER, "neo4j",    JSAP.REQUIRED, 'u', "user",     "The neo4j user"),
                        new FlaggedOption("password",   JSAP.STRING_PARSER, "reactome", JSAP.REQUIRED, 'p', "password", "The neo4j password")
                }
        );
        JSAPResult config = jsap.parse(args);
        if (jsap.messagePrinted()) System.exit(1);

        System.setProperty("neo4j.user", config.getString("user"));
        System.setProperty("neo4j.password", config.getString("password"));

        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration.class);
        GenericService genericService = context.getBean(GenericService.class);

        Reflections reflections = new Reflections(QualityAssuranceAbstract.class.getPackage().getName());
        Set<Class<?>> tests = reflections.getTypesAnnotatedWith(QATest.class);
        for (Class test : tests) {
            try {
                Object object = test.newInstance();
                QualityAssurance qATest =  (QualityAssurance) object;
                qATest.run(genericService);
            } catch (InstantiationException|IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }
}
