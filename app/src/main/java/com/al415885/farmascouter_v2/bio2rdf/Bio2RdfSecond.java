package com.al415885.farmascouter_v2.bio2rdf;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Bio2RdfSecond {
    @SerializedName("http://bio2rdf.org/drugbank_vocabulary:ddi-interactor-in")
    private List<Bio2RdfThirdInteractions> listInteractions;

    public Bio2RdfSecond(List<Bio2RdfThirdInteractions> listInteractions){
        this.listInteractions = listInteractions;
    }

    public List<Bio2RdfThirdInteractions> getListInteractions() {
        return listInteractions;
    }
}
