package com.al415885.farmascouter_v2.models.bio2rdf.previousLevel;

import java.util.List;

public class Bio2RdfPrevSecond {

    private List<Bio2RdfPrevThird> bindings;

    public Bio2RdfPrevSecond(List<Bio2RdfPrevThird> bindings){
        this.bindings = bindings;
    }

    public List<Bio2RdfPrevThird> getBindings() {
        return this.bindings;
    }
}
