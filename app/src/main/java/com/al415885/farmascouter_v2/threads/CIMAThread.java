package com.al415885.farmascouter_v2.threads;

import android.content.Context;
import android.media.JetPlayer;

import com.al415885.farmascouter_v2.MainActivity;
import com.al415885.farmascouter_v2.MedPSuministro;
import com.al415885.farmascouter_v2.MedRCambios;
import com.al415885.farmascouter_v2.Medicamento;
import com.al415885.farmascouter_v2.results.ResultsMed;
import com.al415885.farmascouter_v2.results.ResultsMedPSuministro;
import com.al415885.farmascouter_v2.results.ResultsRCambios;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CIMAThread extends Thread implements Runnable{

    // Class-specific variables
    private String url;
    private int threadType;
    private Context context;
    private Object gsonResp;
    private Thread UIThread;
    private List<ResultsMedPSuministro> rvListPSum;
    private List<ResultsRCambios> rvListRCambios;
    private List<ResultsMed> rvListMed;
    private String searchMedName;

    // Codes for threads
    private static final int PSUMINISTRO = 0;
    private static final int RCAMBIOS = 1;
    private static final int DRUGSEARCH = 2;

    // URLs for access to different information
    private static final String URLPSUMINISTRO =
            "https://cima.aemps.es/cima/rest/psuministro";
    private String URLRCAMBIOS =
            "https://cima.aemps.es/cima/rest/registroCambios?fecha=";
    private static final String URLSEARCHMEDNAME =
            "https://cima.aemps.es/cima/rest/medicamentos?nombre=";

    // HTTP needed variables
    private RequestQueue requestQueue;

    // Class constructor
    public CIMAThread(int threadType, Context context, Thread UIThread){
        this.threadType = threadType;
        this.context = context;
        this.UIThread = UIThread;
        this.requestQueue = Volley.newRequestQueue(this.context);
        this.rvListPSum = new ArrayList<>();
        this.rvListMed = new ArrayList<>();
        this.rvListRCambios = new ArrayList<>();
    }

    private void httpRequest(String url, Class<?> cl){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Correct errors at the response
                        if(response.charAt(0) != '{'){
                            response = "{" + response;
                        }
                        if (response.substring(response.length()-2, response.length()).equals("}}")){
                            response = response.substring(0, response.length()-2) + "}";
                        }
                        Gson gson = new Gson();
                        gsonResp = gson.fromJson(response, cl);
                        switch (threadType){
                            case PSUMINISTRO:
                                MedPSuministro medPSum = (MedPSuministro) gsonResp;
                                List<ResultsMedPSuministro> resPSum = medPSum.getResultados();
                                if(!resPSum.isEmpty()) {
                                    for (int i = 0; i < resPSum.size(); i++)
                                        rvListPSum.add(resPSum.get(i));
                                    if(medPSum.pagina < 30)
                                        setPaginaURL(medPSum.pagina + 1);
                                    else
                                        UIThread.start();
                                }
                                else{
                                    UIThread.start();
                                }
                                break;
                            case RCAMBIOS:
                                MedRCambios medRCambios = (MedRCambios) gsonResp;
                                List<ResultsRCambios> resRcambios = medRCambios.getResultados();
                                if(!resRcambios.isEmpty()) {
                                    for (int i = 0; i < resRcambios.size(); i++)
                                        rvListRCambios.add(resRcambios.get(i));
                                    if(medRCambios.pagina < 30)
                                        setPaginaURL(medRCambios.pagina + 1);
                                    else
                                        UIThread.start();
                                }
                                else{
                                    UIThread.start();
                                }
                                break;
                            case DRUGSEARCH:
                                Medicamento medicamento = (Medicamento) gsonResp;
                                List<ResultsMed> resMed = medicamento.getResultados();
                                if(!resMed.isEmpty()) {
                                    for (int i = 0; i < resMed.size(); i++)
                                        rvListMed.add(resMed.get(i));
                                    if(medicamento.pagina < 30)
                                        setPaginaURL(medicamento.pagina + 1);
                                    else
                                        UIThread.start();
                                }
                                else{
                                    UIThread.start();
                                }
                            default:
                                break;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }

    public void run(){
        switch (this.threadType){
            case PSUMINISTRO:
                this.url = URLPSUMINISTRO;
                httpRequest(this.url, MedPSuministro.class);
                break;
            case RCAMBIOS:
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                String date = dateFormat.format(cal.getTime());
                this.URLRCAMBIOS = this.URLRCAMBIOS + date;
                this.url = URLRCAMBIOS + "&pagina=1";
                httpRequest(this.url, MedRCambios.class);
                break;
            case DRUGSEARCH:
                this.url = URLSEARCHMEDNAME + this.searchMedName +
                        "&pagina=1";
                httpRequest(this.url, Medicamento.class);
                break;
            default:
                break;
        }
    }

    public void setSearchMedName(String searchMedName){
        this.searchMedName = searchMedName;
    }

    public Object getResponse(){
        return this.gsonResp;
    }

    public List<ResultsMed> getRvListMed() {
        return this.rvListMed;
    }

    public List<ResultsMedPSuministro> getRvListPSum() {
        return this.rvListPSum;
    }

    public List<ResultsRCambios> getRvListRCambios() {
        return this.rvListRCambios;
    }

    public void setPaginaURL(int pagina){
        switch (this.threadType){
            case PSUMINISTRO:
                this.url = URLPSUMINISTRO.substring(0, 43) + "?pagina=" + String.valueOf(pagina);
                httpRequest(this.url, MedPSuministro.class);
                break;
            case RCAMBIOS:
                this.url = this.url.substring(0, 72) + String.valueOf(pagina);
                httpRequest(this.url, MedRCambios.class);
                break;
            case DRUGSEARCH:
                this.url = URLSEARCHMEDNAME.substring(0, 52) + this.searchMedName +
                        "&pagina=" + String.valueOf(pagina);
                httpRequest(this.url, Medicamento.class);
                break;
            default:
                break;
        }
    }
}
