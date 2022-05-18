package com.al415885.farmascouter_v2;

import com.al415885.farmascouter_v2.results.ResultsMedPSuministro;
import com.al415885.farmascouter_v2.results.ResultsRCambios;

import java.util.List;

public class MedRCambios {

    // Class-specific variables
    public int totalFilas, pagina, tamanioPagina;
    public List<ResultsRCambios> resultados;

    /**
     * Class constructor
     * @param totalFilas
     * @param pagina
     * @param tamanioPagina
     * @param resultados
     */
    public MedRCambios(int totalFilas, int pagina, int tamanioPagina,
                          List<ResultsRCambios> resultados){
        this.totalFilas = totalFilas;
        this.pagina = pagina;
        this.tamanioPagina = tamanioPagina;
        this.resultados = resultados;
    }

    public List<ResultsRCambios> getResultados(){
        return this.resultados;
    }
}
