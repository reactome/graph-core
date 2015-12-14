package uk.ac.ebi.reactome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.reactome.data.IntactParser;
import uk.ac.ebi.reactome.data.ReactomeImporter;
import uk.ac.ebi.reactome.data.ReactomeParser;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.service.GenericService;
import uk.ac.ebi.reactome.service.ImportService;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 13.11.15.
 */
@RestController
public class ImportController {

    @Autowired
    private ReactomeParser reactomeParser;

    @Autowired
    private IntactParser intactParser;

    @Autowired
    private GenericService genericService;

    @Autowired
    private ImportService importService;

    @Autowired
    private ReactomeImporter reactomeImporter;

    @RequestMapping("/loadAll")
    public void loadAll() {
        reactomeParser.loadAll();
    }

    @RequestMapping(value="/load/{id:.*}")
    public void load(@PathVariable String id) throws Exception {
        genericService.cleanDatabase();

        DatabaseObject obj = reactomeImporter.loadObject(id);
        importService.getOrCreate(obj);
        System.out.println("bla");

//        if (id.startsWith("R") ) {
//            reactomeParser.load(id);
//        } else {
//            reactomeParser.load(Long.parseLong(id));
//        }
    }

    @RequestMapping("/cleanDb")
    public void clean() {
        genericService.cleanDatabase();
    }

    @RequestMapping("loadIntact")
    public void loadIntact() {
        intactParser.parseIntactfile();
    }

    @RequestMapping("createConstraints")
    public void createConstraints() {
        reactomeParser.createConstraints();
    }
}
