package uk.ac.ebi.reactome.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.ebi.reactome.domain.model.Interactor;
import uk.ac.ebi.reactome.service.InteractorService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by flo on 28.10.15.
 */
@Component
public final class IntactParser {

    private static final String INTACT_FILE_URL = "ftp://ftp.ebi.ac.uk/pub/databases/intact/current/psimitab/intact-micluster.txt";

    @Autowired
    private InteractorService interactorService;

    public void parseIntactfile() {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL(INTACT_FILE_URL).openStream()));
            String line = in.readLine();
            while ((line = in.readLine()) != null) {
                String[] array = line.split("\\t");
                addLineToDatabase(array);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void addLineToDatabase (String[] array) {

        interactorService.merge(new Interactor(array[0], array[4]));
        interactorService.merge(new Interactor(array[1], array[5]));
        Double score = Double.parseDouble(array[14].replaceAll("[^0-9.]",""));
        interactorService.createInteraction(array[0], array[1], score);
    }
}
