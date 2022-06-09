package com.al415885.farmascouter_v2.models.rxnorm.secondlevel;

import com.al415885.farmascouter_v2.models.rxnorm.thirdlevel.RXNORMThird;

import java.util.List;

public class RXNORMSecond {

    private List<RXNORMThird> interactionType;

    public RXNORMSecond(List<RXNORMThird> interactionType){
        this.interactionType = interactionType;
    }

    public List<RXNORMThird> getInteractionType() {
        return this.interactionType;
    }
}
