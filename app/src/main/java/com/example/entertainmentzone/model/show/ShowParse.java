package com.example.entertainmentzone.model.show;

import com.example.entertainmentzone.network.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowParse {

    public static List<ShowInfo> getShow(String json)
    {
        List<ShowInfo> show = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resultArray = jsonObject.getJSONArray("results");
            for(int i=0;i<resultArray.length();i++)
            {
                ShowInfo showInfo = new ShowInfo();
                JSONObject showJsonObject = resultArray.getJSONObject(i);
                showInfo.setShId(showJsonObject.get("id").toString());
                showInfo.setShName(showJsonObject.get("name").toString());
                showInfo.setShOverview(showJsonObject.get("overview").toString());
                showInfo.setShRating(showJsonObject.get("vote_average").toString());
                showInfo.setShPopularity(showJsonObject.get("popularity").toString());
                showInfo.setShPoster_path(NetworkUtils.imageString(showJsonObject.get("poster_path").toString()));
                showInfo.setShBackdrop_path(NetworkUtils.imageString(showJsonObject.get("backdrop_path").toString()));

                show.add(showInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return show;

    }
}
