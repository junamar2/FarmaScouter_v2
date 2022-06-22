package com.al415885.farmascouter_v2.models.bio2rdf.secondlevel;

import com.al415885.farmascouter_v2.models.bio2rdf.thirdlevel.Bio2RdfThirdInteractions;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Bio2RdfSecond {
    @SerializedName("http://bio2rdf.org/drugbank_vocabulary:ddi-interactor-in")
    private List<Bio2RdfThirdInteractions> listInteractions;
    @SerializedName("http://purl.org/dc/terms/title")
    private Bio2RdfSecondTitle title;
    @SerializedName("http://purl.org/dc/terms/description")
    private Bio2RdfSecondDescription description;

    public Bio2RdfSecond(List<Bio2RdfThirdInteractions> listInteractions, Bio2RdfSecondTitle title,
                         Bio2RdfSecondDescription description){
        this.listInteractions = listInteractions;
        this.title = title;
        this.description = description;
    }

    public List<Bio2RdfThirdInteractions> getListInteractions() {
        return listInteractions;
    }

    public Bio2RdfSecondTitle getTitle() {
        return this.title;
    }

    public Bio2RdfSecondDescription getDescription() {
        return this.description;
    }
}
