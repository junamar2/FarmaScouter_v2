package com.al415885.farmascouter_v2.models.rxnorm.thirdlevel;

import com.al415885.farmascouter_v2.models.rxnorm.fourthlevel.RXNORMFourth;

import java.util.List;

public class RXNORMThird {

    private List<RXNORMFourth> interactionPair;

    public RXNORMThird(List<RXNORMFourth> interactionPair){
        this.interactionPair = interactionPair;
    }

    public List<RXNORMFourth> getInteractionPair() {
        return this.interactionPair;
    }
}
