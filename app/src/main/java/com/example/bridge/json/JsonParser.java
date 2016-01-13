package com.example.bridge.json;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by bridgelabz5 on 13/1/16.
 */
public class JsonParser {

    static InputStream iStream = null;
    static JSONArray jarray = null;
    static String json = " ";

    public JsonParser(){

    }

    public JSONArray getJSONFromUrl(String url){
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200){
                HttpEntity entity= response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine())!=null ){
                    builder.append(line);
                }
            }
            else {
                Log.e("===>","Failed To Download File");
            }

        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            jarray = new JSONArray(builder.toString());
        }catch (JSONException e){
            Log.e("JSON PARSER","ERROR PARSING DATA " + e.toString());
        }
        return  jarray;
    }
}
