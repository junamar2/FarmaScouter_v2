package com.al415885.farmascouter_v2.threads;

import android.content.Context;

import com.al415885.farmascouter_v2.models.umls.firstlevel.UMLSFirst;
import com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.fourthlevel.MTHFourthAtoms;
import com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.thirdlevel.MTHThirdAtoms;
import com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.fourthlevel.MTHFourthDefinitions;
import com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.thirdlevel.MTHThirdDefinitions;
import com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.thirdlevel.rxnorm.secondlevel.RXNORMSecondAtom;
import com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.thirdlevel.rxnorm.firstlevel.RXNORMFirstAtoms;
import com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.thirdlevel.snomedct.secondlevel.SNOMEDCTSecond;
import com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.thirdlevel.snomedct.firstlevel.SNOMEDCTFirst;
import com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.thirdlevel.snomedct.fourthlevel.SNOMEDCTFourthRelations;
import com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.thirdlevel.snomedct.thirdlevel.SNOMEDCTThirdRelations;
import com.al415885.farmascouter_v2.models.umls.thirdlevel.UMLSThird;
import com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.secondlevel.MTHSecond;
import com.al415885.farmascouter_v2.models.umls.thirdlevel.mth.firstlevel.MTHFirst;
import com.al415885.farmascouter_v2.models.umls.secondlevel.UMLSSecond;
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

public class UMLSThread extends Thread implements Runnable{

    private String url;
    private int threadType;
    private Context context;
    private Thread UIThread;
    private RXNORMThread rxnormThread;
    private static final String apiKey = "c6436cfb-eb6e-4e19-ab44-277977ae5a2e";
    private String language;

    private RequestQueue requestQueue;

    // Information about the drug
    private String drug;
    private String definition;
    private List<SNOMEDCTFourthRelations> relations;
    private String RXNORMui;

    // Thread types
    private static final int INTERACTIONSTHREAD = 1;
    private static final int SEARCHUMLSTHREAD = 0;


    // Class constructor
    public UMLSThread(int threadType, Context context, Thread UIThread, String drug, RXNORMThread rxnormThread){
        this.threadType = threadType;
        this.context = context;
        this.UIThread = UIThread;
        this.drug = drug;
        this.requestQueue = Volley.newRequestQueue(this.context);
        this.relations = new ArrayList<>();
        this.language = Locale.getDefault().getLanguage();
        this.rxnormThread = rxnormThread;
    }

    private String BASEURL =
            "https://uts-ws.nlm.nih.gov/rest/search/current?apiKey=" + apiKey;
    public void run() {
        this.url = BASEURL + "&string=" + this.drug;
        UMLSRequest(this.url, UMLSFirst.class);
    }

    private void UMLSRequest(String url, Class<?> cl) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Object gsonResp = gson.fromJson(response, cl);
                        UMLSFirst UMLSFirst = (UMLSFirst) gsonResp;
                        UMLSSecond umlsSecond = UMLSFirst.getResult();
                        List<UMLSThird> umlsThird = umlsSecond.getResults();
                        for(int i = 0; i < umlsThird.size(); i++){
                            if(umlsThird.get(i).getRootSource().equals("MTH")) {
                                MTHRequest(umlsThird.get(i));
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

    public void MTHRequest(UMLSThird UMLSThird){
        String uri = UMLSThird.getUri() + "?apiKey=" + apiKey;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Object gsonResp = gson.fromJson(response, MTHFirst.class);
                        MTHFirst mthFirst = (MTHFirst) gsonResp;
                        MTHSecond mthSecond = mthFirst.getResult();
                        definitionsRequest(mthSecond);
                        atomsRequest(mthSecond);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void definitionsRequest(MTHSecond mthSecond){
        String definitions = mthSecond.getDefinitions() + "?apiKey=" + apiKey;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, definitions,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Object gsonResp = gson.fromJson(response, MTHThirdDefinitions.class);
                        MTHThirdDefinitions mthResultBase = (MTHThirdDefinitions) gsonResp;
                        List<MTHFourthDefinitions> definitions = mthResultBase.getResult();
                        for(int i = 0; i < definitions.size(); i++){
                            if(definitions.get(i).getRootSource().equals("MSHSPA") &&
                                    language.equals(new Locale("es").getLanguage())) {
                                definition = definitions.get(i).getValue();
                                break;
                            }
                            else if(definitions.get(i).getRootSource().equals("MSH") &&
                                    language.equals(new Locale("en").getLanguage())){
                                definition = definitions.get(i).getValue();
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

    public void atomsRequest(MTHSecond mthSecond){
        String uri = "";
        if(this.threadType == 0) {
            if (this.language.equals(new Locale("es").getLanguage()))
                uri = mthSecond.getAtoms() + "?apiKey=" + apiKey + "&sabs=SCTSPA";
            else if (this.language.equals(new Locale("en").getLanguage()))
                uri = mthSecond.getAtoms() + "?apiKey=" + apiKey + "&sabs=SNOMEDCT_US";
        }
        else{
            uri = mthSecond.getAtoms() + "?apiKey=" + apiKey + "&sabs=RXNORM";
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Object gsonResp = gson.fromJson(response, MTHThirdAtoms.class);
                        MTHThirdAtoms mthThirdAtoms = (MTHThirdAtoms) gsonResp;
                        List<MTHFourthAtoms> atoms = mthThirdAtoms.getResult();
                        for(int i = 0; i < atoms.size(); i++){
                            if(threadType == 0) {
                                if (atoms.get(i).getName().toLowerCase(Locale.ROOT).equals(mthSecond.getName().toLowerCase(Locale.ROOT))
                                        && language.equals(new Locale("en").getLanguage())) {
                                    SNOMEDCTRequest(atoms.get(i));
                                    break;
                                } else if (atoms.get(i).getName().toLowerCase(Locale.ROOT).equals(drug)
                                        && language.equals(new Locale("es").getLanguage())) {
                                    SNOMEDCTRequest(atoms.get(i));
                                    break;
                                }
                            }
                            else{
                                RXNORMRequest(atoms.get(i));
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

    public void SNOMEDCTRequest(MTHFourthAtoms mthFourthAtoms){
        String sourceConcept = mthFourthAtoms.getSourceConcept() + "?apiKey=" + apiKey;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, sourceConcept,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Object gsonResp = gson.fromJson(response, SNOMEDCTFirst.class);
                        SNOMEDCTFirst snomedctFirst = (SNOMEDCTFirst) gsonResp;
                        SNOMEDCTSecond atom = snomedctFirst.getResult();
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
                        Object gsonResp = gson.fromJson(response, SNOMEDCTThirdRelations.class);
                        SNOMEDCTThirdRelations snomedctThirdRelations = (SNOMEDCTThirdRelations) gsonResp;
                        List<SNOMEDCTFourthRelations>  listRelations = snomedctThirdRelations.getResult();
                        for(int i = 0; i < listRelations.size(); i++)
                            relations.add(listRelations.get(i));
                        int pageNumber = snomedctThirdRelations.getPageNumber();
                        int pageCount = (snomedctThirdRelations.getPageCount() - 1) + pageNumber;
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

    public void RXNORMRequest(MTHFourthAtoms mthFourthAtoms){
        String sourceConcept = mthFourthAtoms.getSourceConcept() + "?apiKey=" + apiKey;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, sourceConcept,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Object gsonResp = gson.fromJson(response, RXNORMFirstAtoms.class);
                        RXNORMFirstAtoms snomedctAtomBase = (RXNORMFirstAtoms) gsonResp;
                        RXNORMSecondAtom atom = snomedctAtomBase.getResult();
                        RXNORMui = atom.getUi();
                        rxnormThread.setRxnormui(RXNORMui);
                        rxnormThread.start();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public String getNextPage(String uri, int pageNumber){
        if(this.language.equals(new Locale("en").getLanguage()))
            return uri.substring(0, 141) + (pageNumber);
        else if(this.language.equals(new Locale("es").getLanguage()))
            return uri.substring(0, 136) + (pageNumber);
        else
            return "";
    }

    public String getDefinition(){
        return this.definition;
    }

    public List<SNOMEDCTFourthRelations> getRelations() {
        return this.relations;
    }

    public String getRXNORMui(){
        return this.RXNORMui;
    }
}
