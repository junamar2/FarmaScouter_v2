package com.al415885.farmascouter_v2.models.bio2rdf.secondlevel;

import com.google.gson.annotations.SerializedName;

public class Bio2RdfSecondTitle {

    @SerializedName("@value")
    private String title;

    public Bio2RdfSecondTitle(String title){
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}
