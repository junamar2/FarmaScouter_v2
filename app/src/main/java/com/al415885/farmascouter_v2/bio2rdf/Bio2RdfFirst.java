package com.al415885.farmascouter_v2.bio2rdf;

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
