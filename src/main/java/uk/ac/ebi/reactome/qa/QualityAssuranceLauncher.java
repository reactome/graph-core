package uk.ac.ebi.reactome.qa;

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

    public static void main(String[] args) {

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
