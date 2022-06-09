package com.al415885.farmascouter_v2.models.cima.firstlevel;

import com.al415885.farmascouter_v2.models.cima.secondlevel.MedRCSecond;

import java.util.List;

/**
 * Class that gets the response of type MedicamentoPSuministro
 */
public class MedRCFirst {

    // Class-specific variables
    public int totalFilas, pagina, tamanioPagina;
    public List<MedRCSecond> resultados;

    /**
     * Class constructor
     * @param totalFilas: int
     * @param pagina: int
     * @param tamanioPagina: int
     * @param resultados: List<ResultsRCambios>
     */
    public MedRCFirst(int totalFilas, int pagina, int tamanioPagina,
                      List<MedRCSecond> resultados){
        this.totalFilas = totalFilas;
        this.pagina = pagina;
        this.tamanioPagina = tamanioPagina;
        this.resultados = resultados;
    }

    /**
     * Method that returns the List of results obtained
     * @return List<ResultsRCambios>
     */
    public List<MedRCSecond> getResultados(){
        return this.resultados;
    }
}
