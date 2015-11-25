package uk.ac.ebi.reactome.service.placeholder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.reactome.domain.model.ReferenceGeneProduct;
import uk.ac.ebi.reactome.exception.ReactomeParserException;
import uk.ac.ebi.reactome.repository.placeholder.ReferenceGeneProductRepository;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 19.11.15.
 */
@Service
public class ReferenceGeneProductService {

    @Autowired
    private ReferenceGeneProductRepository referenceGeneProductRepository;

    public Iterable<ReferenceGeneProduct> getProteins(Collection<Long> dbIds) {
        return referenceGeneProductRepository.getProteins(dbIds);
    }

    public void doNothing() throws Exception {
        System.out.println("this method does nothing");

            bla();

    }
    public void bla() throws Exception {
        throw new ReactomeParserException("bla");
    }
}
