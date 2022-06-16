package com.al415885.farmascouter_v2.models.bio2rdf.thirdlevel.interactions.secondlevel;

import com.al415885.farmascouter_v2.models.bio2rdf.thirdlevel.interactions.thirdlevel.InteractionBio2RdfThird;
import com.google.gson.annotations.SerializedName;

public class InteractionBio2RdfSecond {

    @SerializedName("@id")
    private String id;
    @SerializedName("http://purl.org/dc/terms/title")
    private InteractionBio2RdfThird third;

    public InteractionBio2RdfSecond(String id, InteractionBio2RdfThird third){
        this.id = id;
        this.third = third;
    }

    public InteractionBio2RdfThird getThird() {
        return this.third;
    }

    public String getId() {
        return this.id;
    }
}
