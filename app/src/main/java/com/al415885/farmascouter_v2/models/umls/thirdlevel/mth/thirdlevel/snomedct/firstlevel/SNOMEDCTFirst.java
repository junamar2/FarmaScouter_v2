package com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.thirdlevel.snomedct.firstlevel;

import com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.thirdlevel.snomedct.secondlevel.SNOMEDCTSecond;

public class SNOMEDCTFirst {

    private int pageSize;
    private int pageNumber;
    private int pageCount;
    private SNOMEDCTSecond result;

    public SNOMEDCTFirst(int pageSize, int pageNumber, int pageCount, SNOMEDCTSecond result){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.result = result;
        this.pageCount = pageCount;
    }

    public SNOMEDCTSecond getResult() {
        return this.result;
    }
}
