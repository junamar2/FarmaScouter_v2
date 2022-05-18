package com.al415885.farmascouter_v2.results;

public class ResultsMedPSuministro{

    // Class-specific variables
    private String cn, nombre, observ;
    private double tipoProblemaSuministro;
    private long fini, ffin;
    private boolean activo;

    public ResultsMedPSuministro(String cn, String nombre, double tipoProblemaSuministro, long fini,
                          long ffin, boolean activo, String observ){
        this.cn = cn;
        this.nombre = nombre;
        this.tipoProblemaSuministro = tipoProblemaSuministro;
        this.fini = fini;
        this.ffin = ffin;
        this.activo = activo;
        this.observ = observ;
    }

    public String getCn(){
        return this.cn;
    }

    public String getNombre(){
        return this.nombre;
    }
    public double getTipoProblemaSuministro(){
        return this.tipoProblemaSuministro;
    }

    public long getFini(){
        return this.fini;
    }

    public long getFfin(){
        return this.ffin;
    }

    public boolean isActivo(){
        return this.activo;
    }

    public String getObserv(){
        return this.observ;
    }
}
