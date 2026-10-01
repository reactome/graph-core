package org.reactome.server.graph.service;

import org.apache.commons.lang3.StringUtils;
import org.reactome.server.graph.domain.model.Species;
import org.reactome.server.graph.repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeciesService {

    @Autowired
    SpeciesRepository speciesRepository;

    @Value("${reactome.species.primary:9606}")
    private String primarySpeciesTaxId;

    public List<Species> getSpecies() {
        List<Species> species = speciesRepository.getSpecies();
        int primaryPos = -1;
        for (int i = 0; i < species.size(); i++) {
            Species s = species.get(i);
            if (s.getTaxId().equals(primarySpeciesTaxId)) {
                primaryPos = i;
                break;
            }
        }
        if (primaryPos != -1) {
            Species primary = species.remove(primaryPos);
            species.add(0, primary);
        }
        return species;
    }

    public List<Species> getAllSpecies() {
        return speciesRepository.getAllSpecies();
    }

    public Species getSpeciesByTaxId(String taxId) {
        return speciesRepository.getSpeciesByTaxId(taxId);
    }

    public Species getSpeciesByDbId(Long dbId) {
        return speciesRepository.getSpeciesByDbId(dbId);
    }

    public Species getSpeciesByName(String name) {
        return speciesRepository.getSpeciesByName(name);
    }

    public Species getSpecies(Object obj) {
        if (obj != null) {
            String num = "";

            if (obj instanceof String) {
                String aux = (String) obj;
                if (!aux.isEmpty()) {
                    if (StringUtils.isNumeric(aux)) {
                        num = aux;
                    } else {
                        return getSpeciesByName(StringUtils.capitalize(aux.toLowerCase().replaceAll("[_ ]+", " ")));
                    }
                }
            } else if (obj instanceof Number && !(obj instanceof Double)) {
                num = "" + obj;
            }

            if (!num.isEmpty()) {
                Species rtn = getSpeciesByTaxId(num);
                if (rtn == null) {
                    rtn = getSpeciesByDbId(Long.valueOf(num));
                }
                return rtn;
            }
        }
        return null;
    }
}
