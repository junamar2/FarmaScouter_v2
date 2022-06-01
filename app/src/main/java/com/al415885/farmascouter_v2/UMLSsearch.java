package com.al415885.farmascouter_v2;

import com.al415885.farmascouter_v2.mappings.UMLSResult;

public class UMLSsearch {
    private int pageSize;
    private int pageNumber;
    private UMLSResult result;

    public UMLSsearch(int pageSize, int pageNumber, UMLSResult result){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.result = result;
    }

    public UMLSResult getResult(){
        return this.result;
    }
}
