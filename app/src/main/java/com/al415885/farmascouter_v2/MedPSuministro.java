package com.al415885.farmascouter_v2;

import com.al415885.farmascouter_v2.results.ResultsMedPSuministro;

import java.util.List;

public class MedPSuministro {

    // Class-specific variables
    public int totalFilas, pagina, tamanioPagina;
    public List<ResultsMedPSuministro> resultados;

    /**
     * Class constructor
     * @param totalFilas
     * @param pagina
     * @param tamanioPagina
     * @param resultados
     */
    public MedPSuministro(int totalFilas, int pagina, int tamanioPagina,
                    List<ResultsMedPSuministro> resultados){
        this.totalFilas = totalFilas;
        this.pagina = pagina;
        this.tamanioPagina = tamanioPagina;
        this.resultados = resultados;
    }

    public List<ResultsMedPSuministro> getResultados(){
        return this.resultados;
    }

}

