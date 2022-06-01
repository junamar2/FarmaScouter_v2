package com.al415885.farmascouter_v2.results;

public class SearchResultsUMLS {

    private String ui;
    private String rootSource;
    private String uri;
    private String name;

    public SearchResultsUMLS(String ui, String rootSource, String uri, String name){
        this.ui = ui;
        this.rootSource = rootSource;
        this.uri = uri;
        this.name = name;
    }

    public String getUri() {
        return this.uri;
    }

    public String getRootSource(){
        return this.rootSource;
    }
}
