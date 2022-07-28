package com.al415885.farmascouter_v2.models.bio2rdf.previousLevel;

public class Bio2RdfResource {

    private String type, value;

    public Bio2RdfResource(String type, String value){
        this.type = type;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
