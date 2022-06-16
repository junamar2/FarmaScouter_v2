package com.al415885.farmascouter_v2.models.bio2rdf.thirdlevel.interactions.firstlevel;

import com.al415885.farmascouter_v2.models.bio2rdf.thirdlevel.interactions.secondlevel.InteractionBio2RdfSecond;
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
