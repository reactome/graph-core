package org.reactome.server.graph.domain.schema;

public enum SchemaDataDownloadType {
    SBML        ("SBML", "https://reactome.org/ContentService/exporter/sbml/__ID__.xml"),
    SBGN        ("SBGN", "https://reactome.org/ReactomeRESTfulAPI/RESTfulWS/sbgnExporter/__ID__"),
    BIOPAX_2    ("BIOPAX2", "https://reactome.org/ReactomeRESTfulAPI/RESTfulWS/biopaxExporter/Level2/__ID__"),
    BIOPAX_3    ("BIOPAX3", "https://reactome.org/ReactomeRESTfulAPI/RESTfulWS/biopaxExporter/Level3/__ID__"),
    PDF         ("PDF", "https://reactome.org/cgi-bin/pdfexporter?DB=gk_current&ID=__ID__"),
    WORD        ("DOCX", "https://reactome.org/cgi-bin/rtfexporter?DB=gk_current&ID=__ID__"),
    PROTEGE     ("OWL", "https://reactome.org/cgi-bin/protegeexporter?DB=gk_current&ID=__ID__");

    private final String format;
    private final String url;

    SchemaDataDownloadType(String format, String url) {
        this.format = format;
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public String getUrl(Long dbId){
        return this.url.replace("__ID__", String.valueOf(dbId));
    }
}