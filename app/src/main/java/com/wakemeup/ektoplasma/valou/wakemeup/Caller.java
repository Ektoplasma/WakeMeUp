package com.wakemeup.ektoplasma.valou.wakemeup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

/**
 * Created by ektoplasma on 06/08/16.
 */
public final class Caller {

    private static String username;
    private static String pseudonyme;
    private static String cookieInstance;
    private static List<String> Ami;
    private static Context ctx;
    private static String currentLink;

    private static String state;

    private final static String PREFS_NAME = "COOKIE_WMU";
    private final static String PREF_SESSION_COOKIE = "session_cookie";
    private final static String PREF_DEFAULT_STRING = "";



    private static SharedPreferences getPrefs() {
        return ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void storePersistantCookieString() {
        String sessionCookie = getPrefs().getString(PREF_SESSION_COOKIE, PREF_DEFAULT_STRING);

        if(!sessionCookie.equals(PREF_DEFAULT_STRING))
        {
            cookieInstance = sessionCookie;
        }
    }

    public static String getState() {
        return state;
    }

    public static void setState(String state) {
        Caller.state = state;
    }

    public static String getCurrentLink() {
        return currentLink;
    }

    public static void setCurrentLink(String currentLink) {
        Caller.currentLink = currentLink;
    }

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

    public static void setCookieInstance(String cookieInstance) { Caller.cookieInstance = cookieInstance; }

    public static List<String> getAmi() {
        return Ami;
    }

    public static void setAmi(List<String> ami) {
        Ami = ami;
    }

    public static void signup(String user, String pseudo, String password){
        Map<String, String> params = new HashMap<>();
        params.put("user", user);
        params.put("pseudonyme", pseudo);
        params.put("password",password);

        username = user;
        pseudonyme = pseudo;

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    System.out.println("Succes: "+succes);

                    assert(succes != null);
                    if(succes.matches("true")) {
                        String cookie = jsonResponse.getString("cookie");
                        System.out.println("Cookie: "+cookie);
                        cookieInstance = cookie;
                        Intent mainIntent = new Intent(ctx, MainActivity.class);
                        ctx.startActivity(mainIntent);
                        SharedPreferences.Editor editor = getPrefs().edit();
                        editor.putString(PREF_SESSION_COOKIE, cookieInstance);
                        editor.apply();
                    }
                    else{
                        username = "";
                        pseudonyme = "";
                        System.out.println("Try again please.");
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
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/create.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);
    }

    public static void signin(String user, String password){
        Map<String, String> params = new HashMap<>();
        params.put("user", user);
        params.put("password",password);

        username = user;

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    System.out.println("Succes: "+succes);

                    assert(succes != null);
                    if(succes.matches("true")) {
                        String cookie = jsonResponse.getString("cookie");
                        String pseudo = jsonResponse.getString("pseudonyme");
                        System.out.println("Cookie: "+cookie + " Pseudonyme: "+pseudo);
                        cookieInstance = cookie;
                        pseudonyme = pseudo;
                        Intent mainIntent = new Intent(ctx, MainActivity.class);
                        ctx.startActivity(mainIntent);
                        SharedPreferences.Editor editor = getPrefs().edit();
                        editor.putString(PREF_SESSION_COOKIE, cookieInstance);
                        editor.apply();
                    }
                    else{
                        username = "";
                        System.out.println("Try again please.");
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
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/login.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);
    }

    public static void setClockSong(final String member){

        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);
        params.put("member",member);
        params.put("link",currentLink);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    assert(succes != null);
                    if(succes.matches("true")) {
                        System.out.println("Succes: "+succes);
                        Toast.makeText(ctx, "a voté !", Toast.LENGTH_LONG).show();
                    }
                    else{
                        System.out.println("Could not set alarm song to "+member);
                        Toast.makeText(ctx, "echec...", Toast.LENGTH_LONG).show();
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
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/vote.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);

    }

    public static void getClockSong(){
        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    assert(succes != null);
                    if(succes.matches("true")) {
                        System.out.println("Succes: "+succes);
                        String link = jsonResponse.getString("link");
                        Toast.makeText(ctx, "Good Morning ! (Message du voteur TODO)", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ctx, YoutubeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("YTLINK", link);
                        ctx.startActivity(intent);
                    }
                    else{
                        System.out.println("Could not get alarm song.");
                        Toast.makeText(ctx, "echec...", Toast.LENGTH_LONG).show();
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
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/awake.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);
    }

    public static void getBddAmi(){

        if(Ami == null)Ami = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    assert(succes != null);
                    if(succes.matches("true")) {
                        JSONObject jsonAmis = jsonResponse.getJSONObject("amis");
                        Iterator x = jsonAmis.keys();

                        int i = 0;

                        while (x.hasNext()){
                            String key = (String) x.next();
                            Ami.add(jsonAmis.get(key).toString());
                            System.out.println("Ami "+i+": "+Ami.get(i));
                            i++;
                        }
                        Set<String> hs = new HashSet<>();
                        hs.addAll(Ami);
                        Ami.clear();
                        Ami.addAll(hs);

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

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);

    }

}
