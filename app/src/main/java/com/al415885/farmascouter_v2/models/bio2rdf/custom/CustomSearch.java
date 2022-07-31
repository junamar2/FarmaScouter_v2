package com.al415885.farmascouter_v2.models.bio2rdf.custom;

import com.google.gson.annotations.SerializedName;

public class CustomSearch {

    @SerializedName("boolean")
    private boolean bool;

    public CustomSearch(boolean bool){
        this.bool = bool;
    }

    public boolean isBool() {
        return this.bool;
    }
}
