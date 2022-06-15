package com.al415885.farmascouter_v2.threads;

import android.content.Context;

import com.al415885.farmascouter_v2.bio2rdf.Bio2RdfFirst;
import com.al415885.farmascouter_v2.bio2rdf.Bio2RdfSecond;
import com.al415885.farmascouter_v2.bio2rdf.Bio2RdfThirdInteractions;
import com.al415885.farmascouter_v2.bio2rdf.InteractionBio2RdfFirst;
import com.al415885.farmascouter_v2.bio2rdf.InteractionBio2RdfSecond;
import com.al415885.farmascouter_v2.bio2rdf.InteractionBio2RdfThird;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.Syntax;

import java.util.ArrayList;
import java.util.List;

public class Bio2RdfThread extends Thread implements Runnable{

    private String url;
    private int threadType;
    private Context context;
    private Thread UIThread;
    private static final String sparqlEndpointUri = "https://bio2rdf.org/sparql",
                                sparqlEndpointAnswerFormat = "&output=application%2Fld%2Bjson";

    private RequestQueue requestQueue;

    // Information about the drug
    private String drugbankui;
    private List<String> interactions;


    // Class constructor
    public Bio2RdfThread(Context context, Thread UIThread){
        this.threadType = threadType;
        this.context = context;
        this.UIThread = UIThread;
        this.requestQueue = Volley.newRequestQueue(this.context);
        this.interactions = new ArrayList<>();
    }

    private String BASEURL =
            "https://rxnav.nlm.nih.gov/REST/interaction/interaction.json?rxcui=";
    public void run() {
        String queryString = "DESCRIBE <http://bio2rdf.org/drugbank:" + this.drugbankui + ">";
        Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
        QueryExecution qe = QueryExecutionFactory.sparqlService(sparqlEndpointUri, query);
        this.url = qe.toString().substring(4) + sparqlEndpointAnswerFormat;
        DRUGBANKRequest(this.url, Bio2RdfFirst.class);
    }

    public void DRUGBANKRequest(String url, Class<?> cl){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Object gsonResp = gson.fromJson(response, Bio2RdfFirst.class);
                        Bio2RdfFirst bio2RdfFirst = (Bio2RdfFirst) gsonResp;
                        List<Bio2RdfSecond> r = bio2RdfFirst.getSecond();
                        List<Bio2RdfThirdInteractions> s = null;
                        int i = 0;
                        while(i < 10 && s == null){
                            Bio2RdfSecond f = r.get(i);
                            s = f.getListInteractions();
                            i++;
                        }
                        for(int j = 0; j < s.size(); j++){
                            Bio2RdfThirdInteractions g = s.get(j);
                            interactionRequest(g.getId(), j, s.size());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void interactionRequest(String uri, int j, int size){
        String queryString = "DESCRIBE <" + uri + ">";
        Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
        QueryExecution qe = QueryExecutionFactory.sparqlService(sparqlEndpointUri, query);
        this.url = qe.toString().substring(4) + sparqlEndpointAnswerFormat;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Object gsonResp = gson.fromJson(response, InteractionBio2RdfFirst.class);
                        InteractionBio2RdfFirst interactionBio2RdfFirst = (InteractionBio2RdfFirst) gsonResp;
                        List<InteractionBio2RdfSecond> interactionBio2RdfSecond = interactionBio2RdfFirst.getSecond();
                        InteractionBio2RdfSecond interactionBio2RdfSecond1 = interactionBio2RdfSecond.get(2);
                        InteractionBio2RdfThird interactionBio2RdfThird = interactionBio2RdfSecond1.getThird();
                        if(interactionBio2RdfThird != null)
                            interactions.add(interactionBio2RdfThird.getValue());
                        if(j == size-1) UIThread.start();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
        String a = ";";
    }

    public void setDRUGBANKui(String drugbankui){
        this.drugbankui = drugbankui;
    }

    public List<String> getInteractions() {
        return this.interactions;
    }
}
