package com.al415885.farmascouter_v2.utils;

import java.io.Serializable;

public class Documento implements Serializable {

    // Class-specific variables
    private int tipo;
    private long fecha;
    private String url, urlHtml;
    private boolean secc;

    // Class constructor
    public Documento(int tipo, String url, String urlHtml, boolean secc, long fecha){
        this.tipo = tipo;
        this.url = url;
        this.urlHtml = urlHtml;
        this.secc = secc;
        this.fecha = fecha;
    }

    public String getUrl(){
        return this.url;
    }
}
