package com.example.entertainmentzone.network;

import android.net.Uri;
import android.os.HandlerThread;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {
    private static final String BASE_URL ="https://api.themoviedb.org/3";
    private static final String TV_SHOW  = "tv";
    private static final String KEY = "api_key";
    private static final String VALUE = "22350417fc94dfcfba15731ed7aa5a61";
    private static final String VIDEO ="videos";
    private static final String REVIEW = "reviews";

    public static URL tvShowURL(String name)
    {
        URL url =null;
        Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath(TV_SHOW)
                .appendPath(name).appendQueryParameter(KEY,VALUE).build();
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String imageString(String image)
    {
        return "https://image.tmdb.org/t/p/w185/"+image;
    }
    public static URL videoURL(String ID)
    {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendPath(TV_SHOW).appendPath(ID)
                .appendPath(VIDEO).appendQueryParameter(KEY,VALUE).build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.e("URL=",url.toString());
        return url;
    }
    public static URL reviewURL(String ID)
    {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendPath(TV_SHOW).appendPath(ID)
                .appendPath(REVIEW).appendQueryParameter(KEY,VALUE).build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.e("URL=",url.toString());
        return url;
    }

    public static String getConnection(URL url)
    {
        try {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line = " ";
            while ((line = bReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
