package com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.thirdlevel.snomedct.thirdlevel;

import com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.thirdlevel.snomedct.fourthlevel.SNOMEDCTFourthRelations;

import java.util.List;

public class SNOMEDCTThirdRelations {

    private int pageSize;
    private int pageNumber;
    private int pageCount;
    private List<SNOMEDCTFourthRelations> result;

    public SNOMEDCTThirdRelations(int pageSize, int pageNumber, int pageCount, List<SNOMEDCTFourthRelations> result){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.result = result;
        this.pageCount = pageCount;
    }

    public List<SNOMEDCTFourthRelations> getResult() {
        return this.result;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }
}
