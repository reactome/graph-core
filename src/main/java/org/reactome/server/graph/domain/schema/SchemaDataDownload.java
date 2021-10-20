package org.reactome.server.graph.domain.schema;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.reactome.server.graph.domain.model.Event;

@SuppressWarnings("unused")
public class SchemaDataDownload {
    private final String contentUrl;
    private final String fileFormat;
    private final String encodingFormat = "UTF-8";

    public SchemaDataDownload(SchemaDataDownloadType dataDownloadType, Event event) {
        this.contentUrl = dataDownloadType.getUrl(event.getDbId());
        this.fileFormat = dataDownloadType.getFormat();
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public String getEncodingFormat() {
        return encodingFormat;
    }

    @JsonGetter(value = "@type")
    public String getType(){
        return "DataDownload";
    }
}
