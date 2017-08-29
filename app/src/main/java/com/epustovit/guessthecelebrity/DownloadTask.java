package com.epustovit.guessthecelebrity;

/**
 * Created by User on 24.08.2017.
 */

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Download content from web site
 */
public class DownloadTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        URL url;
        HttpURLConnection urlConnection =null;

        try {
            url = new URL(urls[0]);

            urlConnection = (HttpURLConnection)url.openConnection();

            InputStream inputStream = urlConnection.getInputStream();

            InputStreamReader reader = new InputStreamReader(inputStream);

            int data = reader.read();

            while (data != -1){
                char current = (char)data;

                result += current;

                data = reader.read();
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
