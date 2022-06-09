package com.al415885.farmascouter_v2.models.umls.thirdlevel;

public class UMLSThird {

    private String ui;
    private String rootSource;
    private String uri;
    private String name;

    public UMLSThird(String ui, String rootSource, String uri, String name){
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
