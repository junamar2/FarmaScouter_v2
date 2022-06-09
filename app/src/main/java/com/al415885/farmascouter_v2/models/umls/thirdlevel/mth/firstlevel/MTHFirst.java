package com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.firstlevel;

import com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.secondlevel.MTHSecond;

public class MTHFirst {
    private int pageSize;
    private int pageNumber;
    private int pageCount;
    private MTHSecond result;

    public MTHFirst(int pageSize, int pageNumber, int pageCount, MTHSecond result){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.result = result;
    }

    public MTHSecond getResult() {
        return this.result;
    }
}
