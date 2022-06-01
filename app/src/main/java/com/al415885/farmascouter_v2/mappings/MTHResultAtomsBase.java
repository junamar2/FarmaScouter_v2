package com.al415885.farmascouter_v2.mappings;

import java.util.List;

public class MTHResultAtomsBase {

    private int pageSize;
    private int pageNumber;
    private int pageCount;
    private List<MTHResultAtoms> result;

    public MTHResultAtomsBase(int pageSize, int pageNumber, int pageCount, List<MTHResultAtoms> result){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.result = result;
        this.pageCount = pageCount;
    }

    public List<MTHResultAtoms> getResult() {
        return this.result;
    }
}
