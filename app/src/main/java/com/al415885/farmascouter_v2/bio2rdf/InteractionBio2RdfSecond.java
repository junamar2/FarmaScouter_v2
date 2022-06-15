package com.al415885.farmascouter_v2.bio2rdf;

import com.google.gson.annotations.SerializedName;

public class InteractionBio2RdfSecond {

    @SerializedName("@id")
    private String id;
    @SerializedName("http://purl.org/dc/terms/title")
    private InteractionBio2RdfThird third;

    public InteractionBio2RdfSecond(InteractionBio2RdfThird third){
        this.third = third;
    }

    public InteractionBio2RdfThird getThird() {
        return third;
    }
}
