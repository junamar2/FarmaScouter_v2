package com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.thirdlevel.snomedct.secondlevel;

public class SNOMEDCTSecond {

    private String relations;
    private String classType;

    public SNOMEDCTSecond(String relations, String classType){
        this.relations = relations;
        this.classType = classType;
    }

    public String getRelations() {
        return this.relations;
    }
}
