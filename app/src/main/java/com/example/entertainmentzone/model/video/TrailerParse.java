package com.example.entertainmentzone.model.video;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrailerParse {
    public static List<TrailerInfo> getTrailer(String json) {

        List<TrailerInfo> trailer = new ArrayList<>() ;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resultArray = jsonObject.getJSONArray("results");
            Log.i("video - length",""+resultArray.length());
            for(int i=0;i<resultArray.length();i++)
            {
                Log.i("video : ",""+i);
                TrailerInfo trailerInfo = new TrailerInfo();
                JSONObject trailerData = resultArray.getJSONObject(i);
                trailerInfo.setShId(trailerData.getString("id"));
                trailerInfo.setShTitle(trailerData.get("name").toString());
                trailerInfo.setShVideoKey(trailerData.get("key").toString());


                trailer.add(trailerInfo);

                Log.v("video::::",trailerInfo.getShVideoKey());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailer;

    }
}
