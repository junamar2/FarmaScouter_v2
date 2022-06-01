package com.al415885.farmascouter_v2.mappings;

import java.util.List;

public class MTHResultDefinitionsBase {
    private int pageSize;
    private int pageNumber;
    private int pageCount;
    private List<MTHResultDefinitions> result;

    public MTHResultDefinitionsBase(int pageSize, int pageNumber, int pageCount, List<MTHResultDefinitions> result){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.result = result;
        this.pageCount = pageCount;
    }

    public List<MTHResultDefinitions> getResult() {
        return this.result;
    }
}
