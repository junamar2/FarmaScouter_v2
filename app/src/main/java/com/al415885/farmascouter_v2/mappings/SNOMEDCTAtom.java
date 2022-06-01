package com.al415885.farmascouter_v2.mappings;

public class SNOMEDCTAtom {

    private String relations;
    private String classType;

    public SNOMEDCTAtom(String relations, String classType){
        this.relations = relations;
        this.classType = classType;
    }

    public String getRelations() {
        return this.relations;
    }
}
