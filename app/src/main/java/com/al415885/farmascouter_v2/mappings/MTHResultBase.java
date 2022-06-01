package com.al415885.farmascouter_v2.mappings;

public class MTHResultBase {
    private int pageSize;
    private int pageNumber;
    private int pageCount;
    private MTHResult result;

    public MTHResultBase(int pageSize, int pageNumber, int pageCount, MTHResult result){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.result = result;
    }

    public MTHResult getResult() {
        return this.result;
    }
}
