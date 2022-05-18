package com.al415885.farmascouter_v2.results;

import java.util.List;

public class ResultsRCambios {

    // Class-specific variables
    private String nregistro;
    private long fecha;
    private int tipoCambio;
    private List<String> cambio;

    public ResultsRCambios(String nregistro, long fecha, int tipoCambio, List<String> cambio){
        this.nregistro = nregistro;
        this.fecha = fecha;
        this.tipoCambio = tipoCambio;
        this.cambio = cambio;
    }

    public String getNregistro(){
        return this.nregistro;
    }

    public long getFecha() {
        return this.fecha;
    }

    public int getTipoCambio() {
        return this.tipoCambio;
    }

    public List<String> getCambio() {
        return this.cambio;
    }
}
