package com.bidyuk.tasks.sunsetsunrise.Models;

import android.os.AsyncTask;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Yura on 01.03.2018.
 */

public class ParseJSONModel {

    DownloadTask downloadTask;

    public void loadData(LatLng latLng, ParseJSONCallback parseJSONCallback)
    {
        downloadTask = new DownloadTask(parseJSONCallback);
        downloadTask.execute("https://api.sunrise-sunset.org/json?lat=" + latLng.latitude
                + "&lng=" + latLng.longitude);
    }

    public interface ParseJSONCallback{

        void onParsed(Pair<String,String> pairOfResult);
    }
    /**
     * DownloadTask is needed for parse
     */
    private class DownloadTask extends AsyncTask<String, Void, Pair<String, String>>
    {
        ParseJSONCallback parseJSONCallback;

        DownloadTask(ParseJSONCallback parseJSONCallback){
            this.parseJSONCallback = parseJSONCallback;
        }

        @Override
        protected Pair<String, String> doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();
                String result ="";
                while (data != -1)
                {
                    result += (char) data;
                    data = inputStreamReader.read();
                }
                    Pair<String,String> pairOfResult = parseJSON(result);
                    return pairOfResult;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Pair<String,String> pairOfResult){
            if (parseJSONCallback != null)
                parseJSONCallback.onParsed(pairOfResult);
        }

        private Pair<String,String> parseJSON(String result)
        {
            String sunset = "", sunrise = "";
            try {
                JSONObject jsonObject = new JSONObject(result);
                jsonObject = jsonObject.getJSONObject("results");
                sunset = jsonObject.getString("sunset");
                sunrise = jsonObject.getString("sunrise");

                return new Pair<>("Sunrise\n" + sunrise,"Sunset\n" + sunset);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }
    }
}
