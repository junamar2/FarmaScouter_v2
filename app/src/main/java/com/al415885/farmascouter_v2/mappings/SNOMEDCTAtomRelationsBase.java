package com.al415885.farmascouter_v2.mappings;

import java.util.List;

public class SNOMEDCTAtomRelationsBase {

    private int pageSize;
    private int pageNumber;
    private int pageCount;
    private List<SNOMEDCTAtomRelations> result;

    public SNOMEDCTAtomRelationsBase(int pageSize, int pageNumber, int pageCount, List<SNOMEDCTAtomRelations> result){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.result = result;
        this.pageCount = pageCount;
    }

    public List<SNOMEDCTAtomRelations> getResult() {
        return this.result;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }
}
