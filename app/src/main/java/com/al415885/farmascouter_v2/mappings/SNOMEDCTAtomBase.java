package com.al415885.farmascouter_v2.mappings;

import java.util.List;

public class SNOMEDCTAtomBase {

    private int pageSize;
    private int pageNumber;
    private int pageCount;
    private SNOMEDCTAtom result;

    public SNOMEDCTAtomBase(int pageSize, int pageNumber, int pageCount, SNOMEDCTAtom result){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.result = result;
        this.pageCount = pageCount;
    }

    public SNOMEDCTAtom getResult() {
        return this.result;
    }
}
