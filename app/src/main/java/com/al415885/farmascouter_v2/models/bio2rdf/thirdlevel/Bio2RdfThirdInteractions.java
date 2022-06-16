package com.al415885.farmascouter_v2.models.bio2rdf.thirdlevel;

import com.google.gson.annotations.SerializedName;

public class Bio2RdfThirdInteractions {

    @SerializedName("@id")
    private String id;

    public Bio2RdfThirdInteractions(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
