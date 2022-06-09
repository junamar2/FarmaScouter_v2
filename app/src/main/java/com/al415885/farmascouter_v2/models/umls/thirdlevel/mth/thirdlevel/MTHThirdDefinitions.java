package com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.thirdlevel;

import com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.fourthlevel.MTHFourthDefinitions;

import java.util.List;

public class MTHThirdDefinitions {
    private int pageSize;
    private int pageNumber;
    private int pageCount;
    private List<MTHFourthDefinitions> result;

    public MTHThirdDefinitions(int pageSize, int pageNumber, int pageCount, List<MTHFourthDefinitions> result){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.result = result;
        this.pageCount = pageCount;
    }

    public List<MTHFourthDefinitions> getResult() {
        return this.result;
    }
}
