package com.al415885.farmascouter_v2.utils;

import java.io.Serializable;

public class Estado implements Serializable {

    private long aut;

    public Estado(long aut) {
        this.aut = aut;
    }

    public long getAut() {
        return this.aut;
    }
}
