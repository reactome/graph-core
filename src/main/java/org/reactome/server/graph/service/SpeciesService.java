package org.reactome.server.graph.service;

import org.apache.commons.lang3.StringUtils;
import org.reactome.server.graph.domain.model.Species;
import org.reactome.server.graph.repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeciesService {

    @Autowired
    SpeciesRepository speciesRepository;

    public List<Species> getSpecies() {
        List<Species> species = speciesRepository.getSpecies();
        int humanPos = -1;
        for (int i = 0; i < species.size(); i++) {
            Species s = species.get(i);
            if (s.getTaxId().equals("9606")) {
                humanPos = i;
                break;
            }
        }
        if (humanPos != -1) {
            Species human = species.remove(humanPos);
            species.add(0, human);
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
