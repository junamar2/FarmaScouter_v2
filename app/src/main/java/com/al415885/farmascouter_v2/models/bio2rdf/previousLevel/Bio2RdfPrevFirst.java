package com.al415885.farmascouter_v2.models.bio2rdf.previousLevel;

public class Bio2RdfPrevFirst {

    private Bio2RdfPrevSecond results;

    public Bio2RdfPrevFirst(Bio2RdfPrevSecond results){
        this.results = results;
    }

    public Bio2RdfPrevSecond getResults() {
        return this.results;
    }
}
