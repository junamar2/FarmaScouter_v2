package com.al415885.farmascouter_v2.mappings;

public class SNOMEDCTAtomRelations {

    private String additionalRelationLabel;
    private String relatedIdName;

    public SNOMEDCTAtomRelations(String additionalRelationLabel, String relatedIdName){
        this.additionalRelationLabel = additionalRelationLabel;
        this.relatedIdName = relatedIdName;
    }

    public String getAdditionalRelationLabel() {
        return this.additionalRelationLabel;
    }

    public String getRelatedIdName() {
        return this.relatedIdName;
    }
}
