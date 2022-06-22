package com.al415885.farmascouter_v2.models.bio2rdf.secondlevel;

import com.google.gson.annotations.SerializedName;

public class Bio2RdfSecondDescription {

    @SerializedName("@value")
    private String description;

    public Bio2RdfSecondDescription(String description){
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
