package uk.ac.ebi.reactome.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.reactome.data.DatabaseObjectFactory;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;

import java.util.List;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 13.11.15.
 */
@RestController
@RequestMapping("/json")
public class GkInstanceController {


    @RequestMapping("/loadFrontPageItems")
    public @ResponseBody List<DatabaseObject> loadAll() {
        return DatabaseObjectFactory.loadFrontPageItems();
    }

    @RequestMapping(value="/load/{id:.*}")
    public @ResponseBody DatabaseObject load(@PathVariable String id) throws Exception {
        return DatabaseObjectFactory.createObject(id);
    }
}
