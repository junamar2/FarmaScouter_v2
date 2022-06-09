package com.al415885.farmascouter_v2.threads;

import android.content.Context;

import com.al415885.farmascouter_v2.models.rxnorm.firstlevel.RXNORMFirst;
import com.al415885.farmascouter_v2.models.rxnorm.secondlevel.RXNORMSecond;
import com.al415885.farmascouter_v2.models.rxnorm.fourthlevel.RXNORMFourth;
import com.al415885.farmascouter_v2.models.rxnorm.thirdlevel.RXNORMThird;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RXNORMThread extends Thread implements Runnable{

    private String url;
    private int threadType;
    private Context context;
    private Thread UIThread;

    private RequestQueue requestQueue;

    // Information about the drug
    private String rxnormui;
    private List<String> interactions;


    // Class constructor
    public RXNORMThread(Context context, Thread UIThread){
        this.threadType = threadType;
        this.context = context;
        this.UIThread = UIThread;
        this.requestQueue = Volley.newRequestQueue(this.context);
        this.interactions = new ArrayList<>();
    }

    private String BASEURL =
            "https://rxnav.nlm.nih.gov/REST/interaction/interaction.json?rxcui=";
    public void run() {
        this.url = this.BASEURL + this.rxnormui;
        RXNORMRequest(this.url, RXNORMFirst.class);
    }

    public void RXNORMRequest(String url, Class<?> cl){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Object gsonResp = gson.fromJson(response, cl);
                        RXNORMFirst rxnormFirst = (RXNORMFirst) gsonResp;
                        List<RXNORMSecond> interactionTypeGroup
                                = rxnormFirst.getInteractionTypeGroup();
                        List<RXNORMThird> interactionType
                                = interactionTypeGroup.get(0).getInteractionType();
                        List<RXNORMFourth> interactionPairs
                                = interactionType.get(0).getInteractionPair();
                        for(int i = 0; i < interactionPairs.size(); i++)
                            interactions.add(interactionPairs.get(i).getDescription());
                        UIThread.start();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void setRxnormui(String rxnormui){
        this.rxnormui = rxnormui;
    }

    public List<String> getInteractions() {
        return this.interactions;
    }
}
