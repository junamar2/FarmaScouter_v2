package com.al415885.farmascouter_v2.utils;

import java.io.Serializable;

public class Vtm implements Serializable {

    // Class-specific variables
    private long id;
    private String nombre;

    public Vtm(long id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }
}
