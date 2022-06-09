package com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.fourthlevel;

public class MTHFourthAtoms {

    private String sourceConcept;
    private String name;

    public MTHFourthAtoms(String sourceConcept, String name){
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
