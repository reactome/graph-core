package uk.ac.ebi.reactome.qa;

import org.neo4j.ogm.session.SessionFactory;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.config.AnnotationDrivenBeanDefinitionParser;
import org.springframework.stereotype.Component;
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
@Import(MyConfiguration.class)

public class QualityAssuranceLauncher {



    public static void main(String[] args) {

//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration.class);
//        context.getBean("GenericService");

        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration.class);
        GenericService genericService = context.getBean(GenericService.class);
//        launcher.genericService.clearCache();
//        context.getBean("")
//        ApplicationContextInitializer
//        JavaConfigApplicationContext ctx = new JavaConfigApplicationContext(MyConfiguration.class);

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
