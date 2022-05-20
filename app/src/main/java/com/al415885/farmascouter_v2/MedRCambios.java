package com.al415885.farmascouter_v2;

import com.al415885.farmascouter_v2.results.ResultsRCambios;

import java.util.List;

/**
 * Class that gets the response of type MedicamentoPSuministro
 */
public class MedRCambios {

    // Class-specific variables
    public int totalFilas, pagina, tamanioPagina;
    public List<ResultsRCambios> resultados;

    /**
     * Class constructor
     * @param totalFilas: int
     * @param pagina: int
     * @param tamanioPagina: int
     * @param resultados: List<ResultsRCambios>
     */
    public MedRCambios(int totalFilas, int pagina, int tamanioPagina,
                          List<ResultsRCambios> resultados){
        this.totalFilas = totalFilas;
        this.pagina = pagina;
        this.tamanioPagina = tamanioPagina;
        this.resultados = resultados;
    }

    /**
     * Method that returns the List of results obtained
     * @return List<ResultsRCambios>
     */
    public List<ResultsRCambios> getResultados(){
        return this.resultados;
    }
}
