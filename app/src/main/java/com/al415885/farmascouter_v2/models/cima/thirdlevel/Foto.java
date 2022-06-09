package com.al415885.farmascouter_v2.models.cima.thirdlevel;

import java.io.Serializable;

public class Foto implements Serializable {

    // Class-specific variables
    private String tipo, url;
    private long fecha;

    public Foto(String tipo, String url, long fecha){
        this.tipo = tipo;
        this.url = url;
        this.fecha = fecha;
    }

    public String getUrl(){
        return this.url;
    }
}
