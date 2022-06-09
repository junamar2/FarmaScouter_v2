package com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.thirdlevel;

import com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.fourthlevel.MTHFourthAtoms;

import java.util.List;

public class MTHThirdAtoms {

    private int pageSize;
    private int pageNumber;
    private int pageCount;
    private List<MTHFourthAtoms> result;

    public MTHThirdAtoms(int pageSize, int pageNumber, int pageCount, List<MTHFourthAtoms> result){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.result = result;
        this.pageCount = pageCount;
    }

    public List<MTHFourthAtoms> getResult() {
        return this.result;
    }
}
