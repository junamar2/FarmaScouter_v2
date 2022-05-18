package com.al415885.farmascouter_v2.utils;

import java.io.Serializable;

public class ViaAdministracion implements Serializable {

    // Class-specific variables
    private int id;
    private String nombre;

    public ViaAdministracion(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }
}
