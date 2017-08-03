package org.reactome.server.graph.domain.schema;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.reactome.server.graph.domain.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class SchemaDataSet {

    private String name;
    private String description;
    private String url;
    private List<String> sameAs;
    private String version;
    private List<String> keywords;
    private List<SchemaCreator> creator = new ArrayList<>();
    private SchemaDataCatalog includedInDataCatalog;
    private List<SchemaDataDownload> distribution = new ArrayList<>();
    private List<String> citation = new ArrayList<>();
    private static final String license = "https://creativecommons.org/licenses/by/4.0/";

    public SchemaDataSet(Event event, Integer version) {
        this.name = event.getDisplayName();
        this.description = event.getSummation() != null ? event.getSummation().get(0).getText() : "";
        this.url = "https://reactome.org/PathwayBrowser/#/" + event.getStId();
        this.version = "" + version;
        this.keywords = Collections.singletonList(event.getSchemaClass());
        for (InstanceEdit instanceEdit : event.getAuthored()) {
            for (Person person : instanceEdit.getAuthor()) {
                this.creator.add(new SchemaPerson(person));
            }
        }
        this.includedInDataCatalog = new SchemaDataCatalog();
        for (SchemaDataDownloadType schemaDataDownloadType : SchemaDataDownloadType.values()) {
            this.distribution.add(new SchemaDataDownload(schemaDataDownloadType, event));
        }
        for (Publication publication : event.getLiteratureReference()) {
            String str;
            if (publication instanceof LiteratureReference) {
                str = ((LiteratureReference) publication).getUrl();
            } else if (publication instanceof Book) {
                str = ((Book) publication).getISBN();
            } else {
                str = publication.getDisplayName();
            }
            if (str != null) this.citation.add(str);
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getSameAs() {
        return sameAs;
    }

    public String getVersion() {
        return version;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public List<SchemaCreator> getCreator() {
        return creator;
    }

    public SchemaDataCatalog getIncludedInDataCatalog() {
        return includedInDataCatalog;
    }

    public List<SchemaDataDownload> getDistribution() {
        return distribution;
    }

    public List<String> getCitation() {
        return citation;
    }

    public String getLicense() {
        return license;
    }

    @JsonGetter(value = "@type")
    public String getType() {
        return "DataSet";
    }

    @JsonGetter(value = "@context")
    public String getContext() {
        return "http://schema.org/";
    }
}
