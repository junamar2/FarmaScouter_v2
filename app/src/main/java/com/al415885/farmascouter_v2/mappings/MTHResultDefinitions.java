package com.al415885.farmascouter_v2.mappings;

public class MTHResultDefinitions {
    private String rootSource, value, classType, sourceOriginated;

    public MTHResultDefinitions(String rootSource, String value, String classType, String sourceOriginated){
        this.rootSource = rootSource;
        this.value = value;
        this.classType = classType;
        this.sourceOriginated = sourceOriginated;
    }

    public String getValue() {
        return this.value;
    }

    public String getRootSource() {
        return this.rootSource;
    }
}
