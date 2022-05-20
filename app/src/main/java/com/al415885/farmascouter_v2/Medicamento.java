package com.al415885.farmascouter_v2;


import com.al415885.farmascouter_v2.results.ResultsMed;

import java.util.List;

/**
 * Class that gets the response of type Medicamento
 */
public class Medicamento {

    // Class-specific variables
    public int totalFilas, pagina, tamanioPagina;
    public List<ResultsMed> resultados;

    /**
     * Class constructor
     * @param totalFilas: int
     * @param pagina: int
     * @param tamanioPagina: int
     * @param resultados: List<ResultsMed>
     */
    public Medicamento(int totalFilas, int pagina, int tamanioPagina, List<ResultsMed> resultados){
        this.totalFilas = totalFilas;
        this.pagina = pagina;
        this.tamanioPagina = tamanioPagina;
        this.resultados = resultados;
    }

    /**
     * Method that returns the List of results obtained
     * @return List<ResultsMed>
     */
    public List<ResultsMed> getResultados(){
        return this.resultados;
    }

}


