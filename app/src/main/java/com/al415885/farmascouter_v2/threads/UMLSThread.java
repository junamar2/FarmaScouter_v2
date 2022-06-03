package com.al415885.farmascouter_v2.threads;

import android.app.appsearch.SearchResult;
import android.content.Context;

import com.al415885.farmascouter_v2.UMLSsearch;
import com.al415885.farmascouter_v2.mappings.MTHResultAtoms;
import com.al415885.farmascouter_v2.mappings.MTHResultAtomsBase;
import com.al415885.farmascouter_v2.mappings.MTHResultDefinitions;
import com.al415885.farmascouter_v2.mappings.MTHResultDefinitionsBase;
import com.al415885.farmascouter_v2.mappings.SNOMEDCTAtom;
import com.al415885.farmascouter_v2.mappings.SNOMEDCTAtomBase;
import com.al415885.farmascouter_v2.mappings.SNOMEDCTAtomRelations;
import com.al415885.farmascouter_v2.mappings.SNOMEDCTAtomRelationsBase;
import com.al415885.farmascouter_v2.results.SearchResultsUMLS;
import com.al415885.farmascouter_v2.mappings.MTHResult;
import com.al415885.farmascouter_v2.mappings.MTHResultBase;
import com.al415885.farmascouter_v2.mappings.UMLSResult;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UMLSThread extends Thread implements Runnable{

    private String url;
    private int threadType;
    private Context context;
    private Thread UIThread;
    private static final String apiKey = "c6436cfb-eb6e-4e19-ab44-277977ae5a2e";

    private RequestQueue requestQueue;

    // Information about the drug
    private String drug;
    private String definition;
    private List<SNOMEDCTAtomRelations> relations;


    // Class constructor
    public UMLSThread(int threadType, Context context, Thread UIThread, String drug){
        this.threadType = threadType;
        this.context = context;
        this.UIThread = UIThread;
        this.drug = drug;
        this.requestQueue = Volley.newRequestQueue(this.context);
        this.relations = new ArrayList<>();
    }

    private String BASEURL =
            "https://uts-ws.nlm.nih.gov/rest/search/current?apiKey=" + apiKey;
    public void run() {
        this.url = BASEURL + "&string=" + this.drug;
        UMLSRequest(this.url, UMLSsearch.class);
    }

    private void UMLSRequest(String url, Class<?> cl) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Object gsonResp = gson.fromJson(response, cl);
                        UMLSsearch umlSsearch = (UMLSsearch) gsonResp;
                        UMLSResult umlsResult = umlSsearch.getResult();
                        List<SearchResultsUMLS> searchResultsUMLS = umlsResult.getResults();
                        for(int i = 0; i < searchResultsUMLS.size(); i++){
                            if(searchResultsUMLS.get(i).getRootSource().equals("MTH")) {
                                MTHRequest(searchResultsUMLS.get(i));
                                break;
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void MTHRequest(SearchResultsUMLS searchResultsUMLS){
        String uri = searchResultsUMLS.getUri() + "?apiKey=" + apiKey;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Object gsonResp = gson.fromJson(response, MTHResultBase.class);
                        MTHResultBase mthResultBase = (MTHResultBase) gsonResp;
                        MTHResult mthResult = mthResultBase.getResult();
                        definitionsRequest(mthResult);
                        atomsRequest(mthResult);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void definitionsRequest(MTHResult mthResult){
        String definitions = mthResult.getDefinitions() + "?apiKey=" + apiKey;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, definitions,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Object gsonResp = gson.fromJson(response, MTHResultDefinitionsBase.class);
                        MTHResultDefinitionsBase mthResultBase = (MTHResultDefinitionsBase) gsonResp;
                        List<MTHResultDefinitions> definitions = mthResultBase.getResult();
                        for(int i = 0; i < definitions.size(); i++){
                            if(definitions.get(i).getRootSource().equals("MSHSPA")) {
                                definition = definitions.get(i).getValue();
                                break;
                            }
                        }
                        String a = "";
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void atomsRequest(MTHResult mthResult){
        String definitions = mthResult.getAtoms() + "?apiKey=" + apiKey + "&sabs=SNOMEDCT_US";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, definitions,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Object gsonResp = gson.fromJson(response, MTHResultAtomsBase.class);
                        MTHResultAtomsBase mthResultAtomsBase = (MTHResultAtomsBase) gsonResp;
                        List<MTHResultAtoms> atoms = mthResultAtomsBase.getResult();
                        for(int i = 0; i < atoms.size(); i++){
                            if(atoms.get(i).getName().toLowerCase(Locale.ROOT).equals(mthResult.getName().toLowerCase(Locale.ROOT))) {
                                SNOMEDCTRequest(atoms.get(i));
                                break;
                            }
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

    public void SNOMEDCTRequest(MTHResultAtoms mthResultAtoms){
        String sourceConcept = mthResultAtoms.getSourceConcept() + "?apiKey=" + apiKey;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, sourceConcept,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Object gsonResp = gson.fromJson(response, SNOMEDCTAtomBase.class);
                        SNOMEDCTAtomBase snomedctAtomBase = (SNOMEDCTAtomBase) gsonResp;
                        SNOMEDCTAtom atom = snomedctAtomBase.getResult();
                        String uri = atom.getRelations() + "?apiKey=" + apiKey + "&pageNumber=1";
                        getAtomRelations(uri);
                        String b = ";";
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void getAtomRelations(String uri){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Object gsonResp = gson.fromJson(response, SNOMEDCTAtomRelationsBase.class);
                        SNOMEDCTAtomRelationsBase snomedctAtomRelationsBase = (SNOMEDCTAtomRelationsBase) gsonResp;
                        List<SNOMEDCTAtomRelations>  listRelations = snomedctAtomRelationsBase.getResult();
                        for(int i = 0; i < listRelations.size(); i++)
                            relations.add(listRelations.get(i));
                        int pageNumber = snomedctAtomRelationsBase.getPageNumber();
                        int pageCount = (snomedctAtomRelationsBase.getPageCount() - 1) + pageNumber;
                        String r = getNextPage(uri, pageNumber + 1);
                        if(pageNumber < pageCount)
                            getAtomRelations(r);
                        else
                            UIThread.start();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public String getNextPage(String uri, int pageNumber){
        return uri.substring(0, 141) + (pageNumber);
    }

    public String getDefinition(){
        return this.definition;
    }

    public List<SNOMEDCTAtomRelations> getRelations() {
        return this.relations;
    }
}
