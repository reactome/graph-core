package uk.ac.ebi.reactome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.reactome.data.ReactomeBatchImporter2;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.service.DatabaseObjectService;
import uk.ac.ebi.reactome.service.GenericService;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 19.01.16.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private DatabaseObjectService databaseObjectService;

    @Autowired
    private GenericService genericService;

    /**
     * Does not belong here, currently only for testing
     */
    @Autowired
    private ReactomeBatchImporter2 reactomeBatchImporter2;

        @RequestMapping(value="/load/{id:.*}")
    public void load(@PathVariable String id) throws Exception {
        DatabaseObject db = genericService.findByStableIdentifier(DatabaseObject.class, id, 0);
    }
}