package com.wakemeup.ektoplasma.valou.wakemeup;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ektoplasma on 06/08/16.
 */
public final class Caller {

    private static String username;
    private static String cookieInstance;
    private static List<String> Ami;
    private static Context ctx;

    public static Context getCtx() {
        return ctx;
    }

    public static void setCtx(Context ctx) {
        Caller.ctx = ctx;
    }


    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Caller.username = username;
    }

    public static String getCookieInstance() {
        return cookieInstance;
    }

    public static void setCookieInstance(String cookieInstance) {
        Caller.cookieInstance = cookieInstance;
    }

    public static List<String> getAmi() {
        return Ami;
    }

    public static void setAmi(List<String> ami) {
        Ami = ami;
    }

    public static void getBddAmi(){

        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //JSONObject jsonResponse = response.getJSONObject("form");
                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    assert(succes != null);
                    if(succes.matches("true")) {
                        JSONArray jsonAmis = jsonResponse.getJSONArray("amis");
                        if (jsonAmis != null)
                        {
                            for (int i=0;i<jsonAmis.length();i++){
                                Ami.add(jsonAmis.get(i).toString());
                                System.out.println("Ami "+i+": "+Ami.get(i));
                            }

                        }
                       // List<String> Ami = jsonResponse.
                        //masterLong = Float.valueOf(jsonResponse.getString("lon"));
                        //masterLat = Float.valueOf(jsonResponse.getString("lat"));

                    }
                    else{
                        System.out.println("Could not fetch friends");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/read.php",params, reponseListener, errorListener);
        //DataRequest requestor = new DataRequest(Request.Method.POST, "http://192.168.45.72/read.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);

    }

}
