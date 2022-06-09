package com.al415885.farmascouter_v2.models.cima.firstlevel;


import com.al415885.farmascouter_v2.models.cima.secondlevel.MedSecond;

import java.util.List;

/**
 * Class that gets the response of type Medicamento
 */
public class MedFirst {

    // Class-specific variables
    public int totalFilas, pagina, tamanioPagina;
    public List<MedSecond> resultados;

    /**
     * Class constructor
     * @param totalFilas: int
     * @param pagina: int
     * @param tamanioPagina: int
     * @param resultados: List<ResultsMed>
     */
    public MedFirst(int totalFilas, int pagina, int tamanioPagina, List<MedSecond> resultados){
        this.totalFilas = totalFilas;
        this.pagina = pagina;
        this.tamanioPagina = tamanioPagina;
        this.resultados = resultados;
    }

    /**
     * Method that returns the List of results obtained
     * @return List<ResultsMed>
     */
    public List<MedSecond> getResultados(){
        return this.resultados;
    }

}


