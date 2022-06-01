package com.al415885.farmascouter_v2.mappings;

public class MTHResultAtoms {

    private String sourceConcept;
    private String name;

    public MTHResultAtoms(String sourceConcept, String name){
        this.sourceConcept = sourceConcept;
        this.name = name;
    }

    public String getSourceConcept() {
        return this.sourceConcept;
    }

    public String getName() {
        return this.name;
    }
}
