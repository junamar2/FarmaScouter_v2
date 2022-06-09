package com.al415885.farmascouter_v2.models.cima.firstlevel;

import com.al415885.farmascouter_v2.models.cima.secondlevel.MedPSSecond;

import java.util.List;

/**
 * Class that gets the response of type MedicamentoPSuministro
 */
public class MedPSFirst {

    // Class-specific variables
    public int totalFilas, pagina, tamanioPagina;
    public List<MedPSSecond> resultados;

    /**
     * Class constructor
     * @param totalFilas: int
     * @param pagina: int
     * @param tamanioPagina: int
     * @param resultados: List<ResultsMedPSuministro>
     */
    public MedPSFirst(int totalFilas, int pagina, int tamanioPagina,
                      List<MedPSSecond> resultados){
        this.totalFilas = totalFilas;
        this.pagina = pagina;
        this.tamanioPagina = tamanioPagina;
        this.resultados = resultados;
    }

    /**
     * Method that returns the List of results obtained
     * @return List<ResultsMedPSuministro>
     */
    public List<MedPSSecond> getResultados(){
        return this.resultados;
    }

}

