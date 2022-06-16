package com.al415885.farmascouter_v2.models.bio2rdf.firstlevel;

import com.al415885.farmascouter_v2.models.bio2rdf.secondlevel.Bio2RdfSecond;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Bio2RdfFirst {

    @SerializedName("@graph")
    private List<Bio2RdfSecond> second;

    public Bio2RdfFirst(List<Bio2RdfSecond> second){
        this.second = second;
    }

    public List<Bio2RdfSecond> getSecond() {
        return second;
    }
}
