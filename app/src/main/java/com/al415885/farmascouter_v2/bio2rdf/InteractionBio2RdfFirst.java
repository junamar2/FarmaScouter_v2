package com.al415885.farmascouter_v2.bio2rdf;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InteractionBio2RdfFirst {

    @SerializedName("@graph")
    private List<InteractionBio2RdfSecond> second;

    public InteractionBio2RdfFirst(List<InteractionBio2RdfSecond> second){
        this.second = second;
    }

    public List<InteractionBio2RdfSecond> getSecond() {
        return second;
    }

}
