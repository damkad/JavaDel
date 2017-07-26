package com.example.kadiridamilola.javadel;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kadiri Tosin on 7/22/2017.
 */


final class QueryDevs {
    private static final String LOG_TAG = QueryDevs.class.getSimpleName();



    public static List<JavaDel> fetchDevData(String JAVADELS){
        List<JavaDel> javaDelList = null;
        URL url = convertToUrl(JAVADELS);
        String JSONResponse;
        try {
            JSONResponse = makeHttpConnect(url);
            javaDelList = ExtractList(JSONResponse);

        }
         catch (IOException e){
             Log.e(LOG_TAG, "error due to on/off, ", e);

         }
        return javaDelList;

    }



    private QueryDevs() {
    }
    //-takes in the url_string and converts to url_URL
    //takes in the url_URL and converts it to JSONResponse
    //takes in bytes of data and converts it to strings
    //takes in JSONResponse and returns a list of java developers
    //defines a method that sums up the process

    private static URL convertToUrl(String url_String) {
        URL url = null;
        try {
            url = new URL(url_String);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Malformed error due to ", e);
        }
        return url;
    }

    private static String makeHttpConnect(URL url_URl) throws IOException {
        String JSONResponse = "";
        if (url_URl == null) {
            return JSONResponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url_URl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                JSONResponse = extractString(inputStream);
            } else {
                Log.e("Bad request", httpURLConnection.getResponseMessage());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error due to on/off, ", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return JSONResponse;

    }

    private static String extractString(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();


        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();
        while (line != null) {
            output.append(line);
            line = bufferedReader.readLine();
        }
        return output.toString();
    }


    private static List<JavaDel> ExtractList(String JSONResponse) {
        List<JavaDel> javaDelList = new ArrayList<>();
        if (JSONResponse.isEmpty()) {
            return javaDelList;
        }
        try {
            JSONObject rootObject = new JSONObject(JSONResponse);
            JSONArray jsonArray = rootObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.length() > 0) {
                    JSONObject subroot = jsonArray.getJSONObject(i);
                    String username = subroot.getString("login");
                    String url = subroot.getString("html_url");
                    //String actual_name = subroot.getString("name");
                    String image_url = subroot.getString("avatar_url");

                    JavaDel javaDel = new JavaDel(username, url, image_url);
                    javaDelList.add(javaDel);
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "error due to JSON exception", e);
        }

        return javaDelList;
    }
}
