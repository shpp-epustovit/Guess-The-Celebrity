package com.epustovit.guessthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by User on 24.08.2017.
 */

public class ImageDownloader extends AsyncTask<String,Void,Bitmap>{

    @Override
    protected Bitmap doInBackground(String... urls) {

        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
            return  myBitmap;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
