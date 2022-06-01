package com.al415885.farmascouter_v2.mappings;

import com.al415885.farmascouter_v2.results.SearchResultsUMLS;

import java.util.List;

public class UMLSResult {

    private String classType;
    private List<SearchResultsUMLS> results;

    public UMLSResult(String classType, List<SearchResultsUMLS> results){
        this.classType = classType;
        this.results = results;
    }

    public List<SearchResultsUMLS> getResults() {
        return this.results;
    }
}
