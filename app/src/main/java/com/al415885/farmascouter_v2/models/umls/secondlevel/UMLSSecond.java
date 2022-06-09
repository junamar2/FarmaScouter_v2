package com.al415885.farmascouter_v2.models.umls.secondlevel;

import com.al415885.farmascouter_v2.models.umls.thirdlevel.UMLSThird;

import java.util.List;

public class UMLSSecond {

    private String classType;
    private List<UMLSThird> results;

    public UMLSSecond(String classType, List<UMLSThird> results){
        this.classType = classType;
        this.results = results;
    }

    public List<UMLSThird> getResults() {
        return this.results;
    }
}
