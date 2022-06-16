package com.al415885.farmascouter_v2.threads;

import android.content.Context;

import com.al415885.farmascouter_v2.models.bio2rdf.firstlevel.Bio2RdfFirst;
import com.al415885.farmascouter_v2.models.bio2rdf.secondlevel.Bio2RdfSecond;
import com.al415885.farmascouter_v2.models.bio2rdf.thirdlevel.Bio2RdfThirdInteractions;
import com.al415885.farmascouter_v2.models.bio2rdf.thirdlevel.interactions.firstlevel.InteractionBio2RdfFirst;
import com.al415885.farmascouter_v2.models.bio2rdf.thirdlevel.interactions.secondlevel.InteractionBio2RdfSecond;
import com.al415885.farmascouter_v2.models.bio2rdf.thirdlevel.interactions.thirdlevel.InteractionBio2RdfThird;
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
    private List<String> ids;
    private String title;
    private List<String> interactions;


    // Class constructor
    public Bio2RdfThread(Context context, Thread UIThread){
        this.threadType = threadType;
        this.context = context;
        this.UIThread = UIThread;
        this.requestQueue = Volley.newRequestQueue(this.context);
        this.interactions = new ArrayList<>();
        this.ids = new ArrayList<>();
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
                        List<Bio2RdfSecond> bio2RdfSecondList = bio2RdfFirst.getSecond();
                        List<Bio2RdfThirdInteractions> bio2RdfThirdInteractionsList = null;
                        int i = 0;
                        while(i < bio2RdfSecondList.size() && bio2RdfThirdInteractionsList == null){
                            Bio2RdfSecond bio2RdfSecond = bio2RdfSecondList.get(i);
                            if(bio2RdfSecond.getTitle() != null)
                                title = bio2RdfSecond.getTitle().getTitle();
                            bio2RdfThirdInteractionsList = bio2RdfSecond.getListInteractions();
                            i++;
                        }
                        for(int j = 0; j < bio2RdfThirdInteractionsList.size(); j++){
                            Bio2RdfThirdInteractions g = bio2RdfThirdInteractionsList.get(j);
                            interactionRequest(g.getId(), j, bio2RdfThirdInteractionsList.size());
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
                        if(interactionBio2RdfThird != null) {
                            interactions.add(interactionBio2RdfThird.getValue());
                            String drugbankui1 = interactionBio2RdfSecond1.getId().substring(37, 44);
                            String drugbankui2 = interactionBio2RdfSecond1.getId().substring(45);
                            if(drugbankui.equals(drugbankui1))
                                ids.add(drugbankui2);
                            else
                                ids.add(drugbankui1);
                        }
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

    public List<String> getIds() {
        return this.ids;
    }

    public String getTitle() {
        return this.title;
    }
}
