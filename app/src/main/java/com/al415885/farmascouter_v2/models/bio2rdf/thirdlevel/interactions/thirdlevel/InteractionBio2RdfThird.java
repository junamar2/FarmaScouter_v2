package com.al415885.farmascouter_v2.models.bio2rdf.thirdlevel.interactions.thirdlevel;

import com.google.gson.annotations.SerializedName;

public class InteractionBio2RdfThird {

    @SerializedName("@value")
    private String value;

    public InteractionBio2RdfThird(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
