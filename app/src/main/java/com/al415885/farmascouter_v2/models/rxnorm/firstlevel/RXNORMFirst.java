package com.al415885.farmascouter_v2.models.rxnorm.firstlevel;

import com.al415885.farmascouter_v2.models.rxnorm.secondlevel.RXNORMSecond;

import java.util.List;

public class RXNORMFirst {

    private String nlmDisclaimer;
    private List<RXNORMSecond> interactionTypeGroup;

    public RXNORMFirst(String nlmDisclaimer, List<RXNORMSecond> interactionType){
        this.nlmDisclaimer = nlmDisclaimer;
        this.interactionTypeGroup = interactionType;
    }

    public List<RXNORMSecond> getInteractionTypeGroup() {
        return this.interactionTypeGroup;
    }
}
