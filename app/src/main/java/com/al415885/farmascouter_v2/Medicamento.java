package com.al415885.farmascouter_v2;


import com.al415885.farmascouter_v2.results.ResultsMed;

import java.util.List;

public class Medicamento {

    // Class-specific variables
    public int totalFilas, pagina, tamanioPagina;
    public List<ResultsMed> resultados;


    public Medicamento(int totalFilas, int pagina, int tamanioPagina,
                          List<ResultsMed> resultados){
        this.totalFilas = totalFilas;
        this.pagina = pagina;
        this.tamanioPagina = tamanioPagina;
        this.resultados = resultados;
    }

    public List<ResultsMed> getResultados(){
        return this.resultados;
    }

}


