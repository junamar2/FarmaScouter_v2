package com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.thirdlevel.rxnorm.firstlevel;

import com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.thirdlevel.rxnorm.secondlevel.RXNORMSecondAtom;

public class RXNORMFirstAtoms {

    private int pageSize;
    private int pageNumber;
    private int pageCount;
    private RXNORMSecondAtom result;

    public RXNORMFirstAtoms(int pageSize, int pageNumber, int pageCount, RXNORMSecondAtom result){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.result = result;
        this.pageCount = pageCount;
    }

    public RXNORMSecondAtom getResult() {
        return this.result;
    }
}
