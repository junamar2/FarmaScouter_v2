package com.al415885.farmascouter_v2.models.bio2rdf.secondlevel;

import com.al415885.farmascouter_v2.models.bio2rdf.thirdlevel.Bio2RdfThirdInteractions;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Bio2RdfSecond {
    @SerializedName("http://bio2rdf.org/drugbank_vocabulary:ddi-interactor-in")
    private List<Bio2RdfThirdInteractions> listInteractions;
    @SerializedName("http://purl.org/dc/terms/title")
    private Bio2RdfSecondTitle title;

    public Bio2RdfSecond(List<Bio2RdfThirdInteractions> listInteractions){
        this.listInteractions = listInteractions;
    }

    public List<Bio2RdfThirdInteractions> getListInteractions() {
        return listInteractions;
    }

    public Bio2RdfSecondTitle getTitle() {
        return this.title;
    }
}
