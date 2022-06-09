package com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.fourthlevel;

public class MTHFourthDefinitions {
    private String rootSource, value, classType, sourceOriginated;

    public MTHFourthDefinitions(String rootSource, String value, String classType, String sourceOriginated){
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
