package uk.ac.ebi.reactome.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
@RestController
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

    @ApiOperation(value = "gets a deflated DatabaseObject for given dbId", nickname = "getDatabaseObjectByDbId", response = DatabaseObject.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DatabaseObject.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(value="/load/{id:.*}", method = RequestMethod.GET, consumes = "text/plain", produces = "application/json")
    public @ResponseBody DatabaseObject load(@PathVariable String id) throws Exception {
         return genericService.findByStableIdentifier(DatabaseObject.class, id, 0);
    }
}