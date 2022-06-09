package com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.thirdlevel.snomedct.fourthlevel;

public class SNOMEDCTFourthRelations {

    private String additionalRelationLabel;
    private String relatedIdName;

    public SNOMEDCTFourthRelations(String additionalRelationLabel, String relatedIdName){
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
