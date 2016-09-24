package com.wakemeup.ektoplasma.valou.wakemeup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ektoplasma on 06/08/16.
 */
public final class Caller {

    private static String username;
    private static String pseudonyme;
    private static String cookieInstance;
    private static List<String> Ami;
    private static List<String> NewAmi;
    private static List<String> NewMessages;
    private static List<String> NewSenders;
    private static Context ctx;
    private static String currentLink;
    private static String state;
    private static boolean instance = false;
    private static List<String> World;

    private final static String PREFS_NAME = "COOKIE_WMU";
    private final static String PREF_SESSION_COOKIE = "session_cookie";
    private final static String PREF_DEFAULT_STRING = "";

    public static List<String> getNewSenders() {
        return NewSenders;
    }

    public static void setNewSenders(List<String> newSenders) {
        NewSenders = newSenders;
    }

    public static List<String> getNewMessages() { return NewMessages; }

    public static void setNewMessages(List<String> newMessages) { NewMessages = newMessages; }

    static List<String> getNewAmi() { return NewAmi; }

    public static void setNewAmi(List<String> newAmi) { NewAmi = newAmi; }

    public static String getPseudonyme() {
        return pseudonyme;
    }

    public static void setPseudonyme(String pseudonyme) {
        Caller.pseudonyme = pseudonyme;
    }

    public static boolean isInstance() {
        return instance;
    }

    public static void setInstance(boolean instance) {
        Caller.instance = instance;
    }

    public static String getState() {
        return state;
    }

    public static void setState(String state) {
        Caller.state = state;
    }

    static String getCurrentLink() {
        return currentLink;
    }

    static void setCurrentLink(String currentLink) {
        Caller.currentLink = currentLink;
    }

    static Context getCtx() {
        return ctx;
    }

    static void setCtx(Context ctx) {
        Caller.ctx = ctx;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Caller.username = username;
    }

    static String getCookieInstance() {
        return cookieInstance;
    }

    public static void setCookieInstance(String cookieInstance) { Caller.cookieInstance = cookieInstance; }

    static List<String> getAmi() {
        return Ami;
    }

    public static void setAmi(List<String> ami) {
        Ami = ami;
    }

    static List<String> getWorld() {
        return World;
    }

    public static void setWorld(List<String> world) {
        World = world;
    }

    private static SharedPreferences getPrefs() {
        return ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    static void storePersistantCookieString() {
        String sessionCookie = getPrefs().getString(PREF_SESSION_COOKIE, PREF_DEFAULT_STRING);

        if(!sessionCookie.equals(PREF_DEFAULT_STRING))
        {
            cookieInstance = sessionCookie;
        }
    }

    static void checkCookie()
    {
        Map<String, String> params = new HashMap<>();
        params.put("cookie", cookieInstance);
        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    System.out.println("Succes: "+succes);

                    if(succes != null && succes.matches("true")) {
                        String pseudo = jsonResponse.getString("pseudo");
                        System.out.println("Pseudonyme: "+pseudo);
                        pseudonyme = pseudo;
                        Intent mainIntent = new Intent(ctx, MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ctx.startActivity(mainIntent);
                    }
                    else{
                        SharedPreferences.Editor editor = getPrefs().edit();
                        editor.putString(PREF_SESSION_COOKIE, PREF_DEFAULT_STRING);
                        editor.apply();
                        cookieInstance = "";
                        pseudonyme = "";
                        System.out.println("Try again please.");
                        Intent signIntent = new Intent(ctx, SignActivity.class);
                        signIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ctx.startActivity(signIntent);

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
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/check.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);

    }

    static void signup(String user, String pseudo, String password){
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

                    if(succes != null && succes.matches("true")) {
                        String cookie = jsonResponse.getString("cookie");
                        System.out.println("Cookie: "+cookie);
                        cookieInstance = cookie;
                        SharedPreferences.Editor editor = getPrefs().edit();
                        editor.putString(PREF_SESSION_COOKIE, cookieInstance);
                        editor.apply();
                        Intent mainIntent = new Intent(ctx, MainActivity.class);
                        ctx.startActivity(mainIntent);
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

    static void signin(String user, String password){
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

                    if(succes != null && succes.matches("true")) {
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

    static void setClockSong(final String member){

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

                    if(succes != null && succes.matches("true")) {
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

    static void getClockSong(){
        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
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

    static void getBddAmi(){

        Ami = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
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

    static void getBddWorld(){

        World = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        JSONObject jsonWorld = jsonResponse.getJSONObject("world");
                        Iterator x = jsonWorld.keys();

                        int i = 0;

                        while (x.hasNext()){
                            String key = (String) x.next();
                            World.add(jsonWorld.get(key).toString());
                            System.out.println("World "+i+": "+World.get(i));
                            i++;
                        }
                        Set<String> hs = new HashSet<>();
                        hs.addAll(World);
                        World.clear();
                        World.addAll(hs);

                    }
                    else{
                        System.out.println("Could not fetch world");
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
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/world.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);

    }

    static void addFriend(final String friend)
    {

        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);
        params.put("friend", friend);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        Toast.makeText(ctx, "Demande d'ajout envoyée.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        System.out.println("Could not send invite to "+friend+".");
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
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/add.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);
    }

    static void getNotif()
    {
        NewAmi = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        JSONObject jsonAmis = jsonResponse.getJSONObject("amis");
                        Iterator x = jsonAmis.keys();

                        int i = 0;

                        while (x.hasNext()){
                            String key = (String) x.next();
                            NewAmi.add(jsonAmis.get(key).toString());
                            System.out.println("NewAmi "+i+": "+NewAmi.get(i));
                            i++;
                        }
                        Set<String> hs = new HashSet<>();
                        hs.addAll(NewAmi);
                        NewAmi.clear();
                        NewAmi.addAll(hs);
                        Intent broadcast = new Intent("ekto.valou.badgebroadcast");
                        broadcast.putExtra("TYPE","friend");
                        broadcast.putExtra("COUNT",String.valueOf(i));
                        ctx.sendBroadcast(broadcast);
                        System.out.println("Broadcast envoyé");
                    }
                    else{
                        Intent broadcast = new Intent("ekto.valou.badgebroadcast");
                        broadcast.putExtra("TYPE","friend");
                        broadcast.putExtra("COUNT",String.valueOf(0));
                        ctx.sendBroadcast(broadcast);
                        System.out.println("Broadcast envoyé");
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
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/notif.php",params, reponseListener, errorListener);
        /* ----- */
        NewMessages = new ArrayList<>();
        NewSenders = new ArrayList<>();
        Map<String, String> params2 = new HashMap<>();
        params2.put("cookie",cookieInstance);

        Response.Listener<JSONObject> reponseListener2= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        JSONObject jsonMessages = jsonResponse.getJSONObject("messages");
                        JSONObject jsonSenders = jsonResponse.getJSONObject("senders");
                        Iterator x = jsonMessages.keys();
                        Iterator y = jsonSenders.keys();

                        int i = 0;

                        while (x.hasNext() && y.hasNext()){
                            String key = (String) x.next();
                            String key_s = (String) y.next();
                            NewMessages.add(jsonMessages.get(key).toString());
                            NewSenders.add(jsonSenders.get(key_s).toString());
                            System.out.println("NewMessages "+i+": "+NewMessages.get(i)+" from : "+NewSenders.get(i));
                            i++;
                        }
                        /*Set<String> hs = new HashSet<>();
                        hs.addAll(NewMessages);
                        NewMessages.clear();
                        NewMessages.addAll(hs);
                        Set<String> hs_m = new HashSet<>();
                        hs_m.addAll(NewSenders);
                        NewSenders.clear();
                        NewSenders.addAll(hs_m);*/
                        Intent broadcast = new Intent("ekto.valou.badgebroadcast");
                        broadcast.putExtra("TYPE","message");
                        broadcast.putExtra("COUNT",String.valueOf(i));
                        ctx.sendBroadcast(broadcast);
                        System.out.println("Broadcast envoyé");
                    }
                    else{
                        Intent broadcast = new Intent("ekto.valou.badgebroadcast");
                        broadcast.putExtra("TYPE","message");
                        broadcast.putExtra("COUNT",String.valueOf(0));
                        ctx.sendBroadcast(broadcast);
                        System.out.println("Broadcast envoyé");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener2 = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest requestor2 = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/notif_msg.php",params2, reponseListener2, errorListener2);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);
        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor2);
    }

    static void acceptFriend(final String friend)
    {

        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);
        params.put("friend", friend);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        Toast.makeText(ctx, "Ami ajouté.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        System.out.println("Could not accept friend "+friend+".");
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
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/accept.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);
    }

    static void refuseFriend(final String friend)
    {

        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);
        params.put("friend", friend);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        Toast.makeText(ctx, "Ami refusé.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        System.out.println("Could not refuse friend "+friend+".");
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
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/refuse.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);
    }
}
