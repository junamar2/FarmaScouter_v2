package com.al415885.farmascouter_v2.models.umls.firstlevel;

import com.al415885.farmascouter_v2.models.umls.secondlevel.UMLSSecond;

public class UMLSFirst {
    private int pageSize;
    private int pageNumber;
    private UMLSSecond result;

    public UMLSFirst(int pageSize, int pageNumber, UMLSSecond result){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.result = result;
    }

    public UMLSSecond getResult(){
        return this.result;
    }
}
