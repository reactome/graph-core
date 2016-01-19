package uk.ac.ebi.reactome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.reactome.data.DatabaseObjectFactory;
import uk.ac.ebi.reactome.data.ReactomeBatchImporter;
import uk.ac.ebi.reactome.data.ReactomeBatchImporter2;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.domain.model.Pathway;
import uk.ac.ebi.reactome.service.DatabaseObjectService;
import uk.ac.ebi.reactome.service.GenericService;
import uk.ac.ebi.reactome.service.ImportService;

import java.lang.reflect.Method;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 14.12.15.
 */
@Controller
@RequestMapping("/import")
public class ImportController {

    @Autowired
    private DatabaseObjectService databaseObjectService;

    @Autowired
    private ImportService importService;

    @Autowired
    private GenericService genericService;



    @Autowired
    private ReactomeBatchImporter reactomeBatchImporter;

    @Autowired
    private ReactomeBatchImporter2 reactomeBatchImporter2;



    @RequestMapping("/test")
    public void test() {
        reactomeBatchImporter.importAll();
    }


    @RequestMapping(value="/load/{id:.*}")
    public void load(@PathVariable String id) throws Exception {
        reactomeBatchImporter.importById(id);

//        DatabaseObject db = genericService.findByStableIdentifier(DatabaseObject.class, "R-HSA-211728", 2);
//        DatabaseObject sb2 = genericService.loadByProperty(Pathway.class, "dbId", db.getDbId());
//        System.out.println("bla");

    }

    @RequestMapping(value="/find/{id:.*}")
    public void find(@PathVariable String id) throws Exception {
        // slow because of the depth ... depth 0 100ms
        // depth 1 200 ms

//        DatabaseObject obj = databaseObjectService.findByStableIdentifier(id);
//        DatabaseObject obj2 = genericService.findByStableIdentifier(DatabaseObject.class,id,1);
        //Depth 2 allready takes 9 seconds
        //Depth 2 retrieves 2284 nodes
        Pathway obj = genericService.loadById(Pathway.class,131590L,1);
        long startTime = System.currentTimeMillis();
        DatabaseObject o = DatabaseObjectFactory.createObject(id);
        long stopTime = System.currentTimeMillis();
        long ms = stopTime - startTime;
        int milliseconds = (int) ms % 1000;
        int seconds = (int) (ms / 1000) % 60;
        int minutes = (int) ((ms / (1000 * 60)) % 60);
        System.out.println(" sec " + seconds + " mil " + milliseconds);
        //gk inst 345 ms


        Method[] methods = Pathway.class.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("set")) {
                System.out.println(method.getName());
            }
        }
        System.out.println( " ");
    }

    @RequestMapping("/cleanDb")
    public void clean() {
        importService.cleanDatabase();
    }

    @RequestMapping("loadIntact")
    public void loadIntact() {
//        intactParser.parseIntactfile();
    }

}
