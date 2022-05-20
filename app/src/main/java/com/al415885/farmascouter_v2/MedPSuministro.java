package com.al415885.farmascouter_v2;

import com.al415885.farmascouter_v2.results.ResultsMedPSuministro;

import java.util.List;

/**
 * Class that gets the response of type MedicamentoPSuministro
 */
public class MedPSuministro {

    // Class-specific variables
    public int totalFilas, pagina, tamanioPagina;
    public List<ResultsMedPSuministro> resultados;

    /**
     * Class constructor
     * @param totalFilas: int
     * @param pagina: int
     * @param tamanioPagina: int
     * @param resultados: List<ResultsMedPSuministro>
     */
    public MedPSuministro(int totalFilas, int pagina, int tamanioPagina,
                    List<ResultsMedPSuministro> resultados){
        this.totalFilas = totalFilas;
        this.pagina = pagina;
        this.tamanioPagina = tamanioPagina;
        this.resultados = resultados;
    }

    /**
     * Method that returns the List of results obtained
     * @return List<ResultsMedPSuministro>
     */
    public List<ResultsMedPSuministro> getResultados(){
        return this.resultados;
    }

}

